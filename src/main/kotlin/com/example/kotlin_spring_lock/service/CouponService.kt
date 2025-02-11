package com.example.kotlin_spring_lock.service

import com.example.kotlin_spring_lock.entity.Coupon
import com.example.kotlin_spring_lock.repository.CouponRepository
import com.example.kotlin_spring_lock.repository.MemberRepository
import jakarta.persistence.OptimisticLockException
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException

@Service
class CouponService(
    private val couponRepository: CouponRepository,
    private val memberRepository: MemberRepository
) {

    // 널처리에 대한 고민 ?, !!
    @Transactional
    fun issueCoupon(memberId: Long): Coupon {
        try{
            // 쿠폰 갯수가 100장 이상이라면 404 반환
            if (couponRepository.count() >= 100) {
                throw ResponseStatusException(HttpStatus.NOT_FOUND, "100장의 쿠폰이 모두 소진되었습니다")
            }

            // 존재하는 회원인지 확인
            val member = memberRepository.findById(memberId)
                .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 회원입니다") }

            // 만약 회원이 쿠폰을 이미 가지고 있다면, 기존 쿠폰 반환
            if (member?.coupon != null) {
                return member.coupon!!
            }

            // 아니라면 쿠폰을 발행
            val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
            val newCouponCode = (1..10)
                .map { chars.random() }
                .joinToString("")

            // 새로운 쿠폰 저장
            val newCoupon = Coupon(couponCode = newCouponCode)
            couponRepository.save(newCoupon)

            // 회원의 쿠폰 정보를 업데이트
            member?.coupon = newCoupon
            member?.has_coupon = true
            memberRepository.save(member!!)

            return newCoupon

        } catch(e: OptimisticLockException) {
            throw ResponseStatusException(HttpStatus.CONFLICT,"동시에 쿠폰을 발급하려는 요청이 발생했습니다. 다시 시도해주세요")
        }

    }
}
package com.example.kotlin_spring_lock.service

import com.example.kotlin_spring_lock.entity.Coupon
import com.example.kotlin_spring_lock.repository.CouponRepository
import com.example.kotlin_spring_lock.repository.MemberRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException

@Service
class CouponService(
    private val couponRepository: CouponRepository,
    private val memberRepository: MemberRepository
) {

    @Transactional
    fun issueCoupon(memberId: Long): Coupon {
        couponRepository.lockCouponsTable()

        // 쿠폰 갯수가 100장 이상이라면 404 반환
        val couponCount = couponRepository.count()
        if (couponCount >= 100) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "쿠폰이 끝났습니다")
        }

        // 존재하는 회원인지 확인
        val member = memberRepository.findById(memberId)
            .orElseThrow { RuntimeException("존재하지 않는 회원입니다") }

        // 만약 회원이 쿠폰을 이미 가지고 있다면, 기존 쿠폰 반환
        if (member != null) {
            member.coupon?.let {
                return it
            }
        }

        // 아니라면 쿠폰을 발행하고 저장
        val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
        val newCouponCode = (1..10)
            .map { chars.random() }
            .joinToString("")

        // 새로운 쿠폰을 Coupon 엔티티로 저장
        val newCoupon = Coupon(couponCode = newCouponCode)

        // 쿠폰 저장
        couponRepository.save(newCoupon)

        // 회원의 쿠폰 정보를 업데이트
        if (member != null) {
            member.coupon = newCoupon
        }
        if (member != null) {
            member.has_coupon = true
        }
        member?.let { memberRepository.save(it) }

        return newCoupon
    }
}

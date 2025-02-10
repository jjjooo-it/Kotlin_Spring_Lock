package com.example.kotlin_spring_lock.controller

import com.example.kotlin_spring_lock.entity.Coupon
import com.example.kotlin_spring_lock.service.CouponService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/coupon")
class CouponController (
    private val couponService: CouponService
){
    @PostMapping("/issue")
    fun issueCoupon(@RequestParam memberId: Long): Coupon {
        // 쿠폰 발급 서비스 호출
        return couponService.issueCoupon(memberId)
    }
}
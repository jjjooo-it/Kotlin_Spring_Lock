package com.example.kotlin_spring_lock.repository

import com.example.kotlin_spring_lock.entity.Coupon
import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query

interface CouponRepository : JpaRepository<Coupon, Long> {
    // 쿠폰을 비관적 락으로 조회
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT c FROM Coupon c")
    fun lockCouponsTable(): List<Coupon>

    fun findByCouponCode(couponCode: String): MutableList<Coupon>
}
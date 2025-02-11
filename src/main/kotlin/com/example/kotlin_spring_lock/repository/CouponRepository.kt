package com.example.kotlin_spring_lock.repository

import com.example.kotlin_spring_lock.entity.Coupon
import org.springframework.data.jpa.repository.JpaRepository

interface CouponRepository : JpaRepository<Coupon, Long> {
}
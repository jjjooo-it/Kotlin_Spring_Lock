package com.example.kotlin_spring_lock.entity
import jakarta.persistence.*

@Entity
@Table(name = "coupons")
class Coupon (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null, // 쿠폰 식별자

    @Column(name = "coupon_code", unique = true, nullable = false)
    var couponCode: String = "", // 쿠폰 코드

    @Version
    @Column(name = "version")
    var version: Long = 0, // 낙관 락을 위한 버전
)
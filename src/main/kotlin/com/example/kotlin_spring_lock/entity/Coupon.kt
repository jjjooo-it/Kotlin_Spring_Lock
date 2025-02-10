package com.example.kotlin_spring_lock.entity
import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "coupons")
class Coupon (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null, // 쿠폰 식별자

    @Column(name = "coupon_code", unique = true, nullable = false)
    var couponCode: String = generateCouponCode(), // 쿠폰 코드 (랜덤 생성 함수 호출)

    @Column(name = "issued", nullable = false)
    var isIssued: Boolean = false, // 발행 여부

    @OneToOne(mappedBy = "coupon")
    var member: Member? = null // 회원
) {

    // 쿠폰 코드 랜덤 생성_ 대소문자, 숫자
   companion object {
       fun generateCouponCode(): String {
           val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
           return (1..10).map { chars.random() }.joinToString("")
       }
   }
}

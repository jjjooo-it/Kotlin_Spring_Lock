package com.example.kotlin_spring_lock.entity

import jakarta.persistence.*;

@Entity
@Table(name = "members")
class Member (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null, // 회원 식별자

    @OneToOne
    @JoinColumn(name = "coupon_id")
    var coupon: Coupon? = null, // 쿠폰

    @Column(name = "issued", nullable = false)
    var hasCoupon: Boolean = false, // 쿠폰 소유 여부
) {
    constructor() : this(null, null, false)
}
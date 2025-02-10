package com.example.kotlin_spring_lock.entity

import jakarta.persistence.*;

@Entity
@Table(name = "members")
class Member (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null, // 회원 식별자

    @Column(name = "has_coupon")
    var has_coupon: Boolean = false,

    @OneToOne
    @JoinColumn(name = "coupon_id")
    var coupon: Coupon? = null, // 쿠폰
)
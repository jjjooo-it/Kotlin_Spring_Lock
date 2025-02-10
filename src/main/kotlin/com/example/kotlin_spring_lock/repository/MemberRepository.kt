package com.example.kotlin_spring_lock.repository

import com.example.kotlin_spring_lock.entity.Member
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MemberRepository : JpaRepository<Member?, Long?> {

}


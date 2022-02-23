package com.snutaek.lugilugiserver.domain.user.repository

import com.snutaek.lugilugiserver.domain.user.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<User, Long?> {
    fun findByUserId(email: String): User?
    fun existsByUserId(email: String): Boolean
}

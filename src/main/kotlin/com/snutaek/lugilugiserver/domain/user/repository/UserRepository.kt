package com.snutaek.lugilugiserver.domain.user.repository

import com.snutaek.lugilugiserver.domain.user.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<User, Long?> {
    fun findByEmail(email: String): User?
    fun existsByEmail(email: String): Boolean
}

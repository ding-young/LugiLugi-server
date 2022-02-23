package com.snutaek.lugilugiserver.domain.user.service

import com.snutaek.lugilugiserver.domain.user.dto.UserDto
import com.snutaek.lugilugiserver.domain.user.model.User
import com.snutaek.lugilugiserver.domain.user.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService (
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {
    fun signup(signupRequest: UserDto.SignupRequest) : User {
        // TODO erase tmp User & implement error handling
        if (userRepository.existsByUserId(signupRequest.userId)) return User("tmp", "tmp", "tmp")
        val encodedPassword = passwordEncoder.encode(signupRequest.password)
        return userRepository.save(User(signupRequest.username, signupRequest.userId, encodedPassword))
    }
}
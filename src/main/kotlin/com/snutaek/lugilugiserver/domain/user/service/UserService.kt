package com.snutaek.lugilugiserver.domain.user.service

import com.snutaek.lugilugiserver.domain.user.dto.UserDto
import com.snutaek.lugilugiserver.domain.user.exception.UserEmailAlreadyExistsException
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
        if (userRepository.existsByEmail(signupRequest.email)) throw UserEmailAlreadyExistsException("이미 가입된 이메일로 회원가입할 수 없습니다")
        val encodedPassword = passwordEncoder.encode(signupRequest.password)
        return userRepository.save(User(signupRequest.email, signupRequest.username, signupRequest.nickname, encodedPassword))
    }
}
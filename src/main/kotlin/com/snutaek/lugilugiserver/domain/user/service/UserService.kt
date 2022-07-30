package com.snutaek.lugilugiserver.domain.user.service

import com.snutaek.lugilugiserver.domain.match.exception.MatchNotFoundException
import com.snutaek.lugilugiserver.domain.match.model.Match
import com.snutaek.lugilugiserver.domain.user.dto.UserDto
import com.snutaek.lugilugiserver.domain.user.exception.UserEmailAlreadyExistsException
import com.snutaek.lugilugiserver.domain.user.exception.UserNotFoundException
import com.snutaek.lugilugiserver.domain.user.model.User
import com.snutaek.lugilugiserver.domain.user.repository.UserRepository
import com.snutaek.lugilugiserver.domain.user.util.RandomCodeGenerator
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService (
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val randomCodeGenerator: RandomCodeGenerator,
) {
    fun signup(signupRequest: UserDto.SignupRequest) : User {
        // TODO erase tmp User & implement error handling
        if (userRepository.existsByEmail(signupRequest.email)) throw UserEmailAlreadyExistsException("이미 가입된 이메일로 회원가입할 수 없습니다")
        val encodedPassword = passwordEncoder.encode(signupRequest.password)
        val code = createUserUniqueCode()
        return userRepository.save(User(signupRequest.email, signupRequest.username, signupRequest.nickname, encodedPassword, code=code))
    }

    fun createUserUniqueCode() : String {
        val code = randomCodeGenerator.generateRandomUniqueUserCode()
        if (userRepository.existsByCode(code)) return createUserUniqueCode()
        return code
    }

    fun findById(id: Long) : User {
        return userRepository.findByIdOrNull(id) ?: throw UserNotFoundException("User $id 에 해당하는 User는 존재하지 않습니다")
    }

    fun editProfile(user: User, editProfileRequest: UserDto.EditProfileRequest) : User {
        val username = editProfileRequest.username
        val nickname = editProfileRequest.nickname
        val intro = editProfileRequest.intro
        // TODO q. refactor..?
        if (username!=null) user.username = username
        if (nickname!=null) user.nickname = nickname
        if (intro!=null) user.username = intro

        return userRepository.save(user)
    }
}
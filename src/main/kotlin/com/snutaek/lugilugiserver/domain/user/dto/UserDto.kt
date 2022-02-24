package com.snutaek.lugilugiserver.domain.user.dto

import com.snutaek.lugilugiserver.domain.user.model.User
import javax.validation.constraints.NotBlank

class UserDto {
    data class SignupRequest(
        @field:NotBlank
        val email: String,
        @field:NotBlank
        val username: String,
        @field:NotBlank
        val nickname: String,
        @field:NotBlank
        val password: String,
    )

    data class BaseResponse(
        val id: Long,
        val email: String,
        val username: String,
        val nickname: String
    ) {
        constructor(user: User) : this(
            id = user.id,
            email = user.email,
            username = user.username,
            nickname = user.nickname
        )

    }

    data class SignupResponse(
        val id: Long,
        val token: String
    )
}
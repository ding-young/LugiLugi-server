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

    data class EditProfileRequest(
        val username: String?,
        val nickname: String?,
    )

    data class BaseResponse(
        val id: Long,
        val email: String,
        val username: String,
        val nickname: String,
        val code: String,
    ) {
        constructor(user: User) : this(
            id = user.id,
            email = user.email,
            username = user.username,
            nickname = user.nickname,
            code = user.code,
        )

    }

    data class SignupResponse(
        val id: Long,
        val token: String
    )

    data class ProfileResponse(
        val username: String,
        val nickname: String,
        // TODO image
    ) {
        constructor(user: User) : this(
            username = user.username,
            nickname = user.nickname,
        )

    }
}
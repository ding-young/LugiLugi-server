package com.snutaek.lugilugiserver.domain.user.dto

import com.snutaek.lugilugiserver.domain.user.model.User
import javax.validation.constraints.NotBlank

class UserDto {
    data class SignupRequest(
        @field:NotBlank
        val userId: String,
        @field:NotBlank
        val username: String,
        @field:NotBlank
        val password: String,
    )

    data class BaseResponse(
        val id: Long,
        val userId: String,
        val username: String
    ) {
        constructor(user: User) : this(
            id = user.id,
            userId = user.userId,
            username = user.username
        )

    }

    data class SignupResponse(
        val id: Long,
        val token: String
    )
}
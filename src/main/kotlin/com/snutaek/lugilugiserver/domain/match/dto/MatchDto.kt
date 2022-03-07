package com.snutaek.lugilugiserver.domain.match.dto

import com.snutaek.lugilugiserver.domain.group.model.Group
import com.snutaek.lugilugiserver.domain.user.dto.UserDto
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

class GroupDto {
    data class CreateMatchRequest(
        @field:NotBlank
        val name: String,
    )

    data class PenaltyRequest(
        @field:NotNull
        val blue_penalty: Boolean,
        val red_penalty: Boolean
    )

    data class PenaltyResponse(
        val penalty: String
    )
}
package com.snutaek.lugilugiserver.domain.group.dto

import com.snutaek.lugilugiserver.domain.group.model.Group
import com.snutaek.lugilugiserver.domain.user.dto.UserDto
import javax.validation.constraints.NotBlank

class GroupDto {
    data class CreateGroupRequest(
        @field:NotBlank
        val name: String,
    )

    data class BaseResponse(
        val id: Long,
        val name: String,
    ) {
        constructor(group: Group) : this(
            id = group.id,
            name = group.name
        )
    }

    data class DetailResponse(
        val id: Long,
        val name: String,
        val members: List<UserDto.BaseResponse>
    ) {
        constructor(group: Group) : this(
            id = group.id,
            name = group.name,
            members = group.members.map {UserDto.BaseResponse(it)}
        )
    }
}
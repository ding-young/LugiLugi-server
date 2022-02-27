package com.snutaek.lugilugiserver.domain.group.api

import com.snutaek.lugilugiserver.domain.group.dto.GroupDto
import com.snutaek.lugilugiserver.domain.group.service.GroupService
import com.snutaek.lugilugiserver.domain.user.model.User
import com.snutaek.lugilugiserver.global.auth.CurrentUser
import com.snutaek.lugilugiserver.global.common.dto.ListResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1/group")
class GroupController (
    private val groupService: GroupService
) {
    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    fun createGroup(@RequestBody createGroupRequest: GroupDto.CreateGroupRequest) : GroupDto.BaseResponse {
        val group = groupService.createGroup(createGroupRequest)
        return GroupDto.BaseResponse(group)
    }

    @GetMapping("/list/")
    fun getGroupList() : ListResponse<GroupDto.BaseResponse> {
        val groups = groupService.getAllGroups()
        return ListResponse(groups.map { GroupDto.BaseResponse(it) })
    }

    @PostMapping("/{group_id}/register/")   // where should it be ??
    fun registerGroupUser(
        @CurrentUser user: User,
        @PathVariable group_id: Long,
    ): GroupDto.DetailResponse {
        val group = groupService.findById(group_id)
        return GroupDto.DetailResponse(groupService.addMember(user, group))
    }
}
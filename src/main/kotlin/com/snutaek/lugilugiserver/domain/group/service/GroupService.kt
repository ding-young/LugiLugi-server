package com.snutaek.lugilugiserver.domain.group.service

import com.snutaek.lugilugiserver.domain.group.dto.GroupDto
import com.snutaek.lugilugiserver.domain.group.exception.GroupNameAlreadyExistsException
import com.snutaek.lugilugiserver.domain.group.exception.GroupNotFoundException
import com.snutaek.lugilugiserver.domain.group.model.Group
import com.snutaek.lugilugiserver.domain.group.repository.GroupRepository
import com.snutaek.lugilugiserver.domain.user.model.User
import com.snutaek.lugilugiserver.domain.user.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class GroupService (
    private val groupRepository: GroupRepository,
    private val userRepository: UserRepository
) {
    fun findById(id: Long) : Group {
        return groupRepository.findByIdOrNull(id) ?: throw GroupNotFoundException("Group $id 에 해당하는 Group은 존재하지 않습니")
    }

    fun createGroup(createGroupRequest: GroupDto.CreateGroupRequest) : Group {
        if (groupRepository.existsByName(createGroupRequest.name)) throw GroupNameAlreadyExistsException("이미 존재하는 그룹명으로 새로운 그룹을 만들 수 없습니다.")
        return groupRepository.save(Group(createGroupRequest.name))  // TODO auto connect user who created Group??
    }

    fun getAllGroups() : List<Group> {
        return groupRepository.findAll()
    }

    // TODO where should this fun be????
    fun addMember(user: User, group: Group) : Group {
        user.group = group
        userRepository.save(user)
        return group
    }
}
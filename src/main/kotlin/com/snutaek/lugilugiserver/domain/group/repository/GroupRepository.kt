package com.snutaek.lugilugiserver.domain.group.repository

import com.snutaek.lugilugiserver.domain.group.model.Group
import org.springframework.data.jpa.repository.JpaRepository

interface GroupRepository : JpaRepository<Group, Long>{
    fun findByName(name: String) : Group?
    fun existsByName(name: String) : Boolean
}
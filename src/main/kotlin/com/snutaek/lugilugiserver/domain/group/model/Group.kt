package com.snutaek.lugilugiserver.domain.group.model

import com.snutaek.lugilugiserver.domain.model.BaseEntity
import javax.validation.constraints.NotBlank
import com.snutaek.lugilugiserver.domain.user.model.User
import javax.persistence.*

@Entity
@Table(name = "lugi_group")
class Group (
    @Column(unique = true)
    @field:NotBlank
    var name: String,

    @OneToMany(mappedBy = "group")   // TODO understand spring jpa and cascade type....
    var members : List<User> = listOf()
) : BaseEntity()
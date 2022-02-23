package com.snutaek.lugilugiserver.domain.user.model

import com.snutaek.lugilugiserver.domain.model.BaseTimeEntity
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Entity
@Table(name = "lugi_user")
class User (

    @field:NotBlank   // null, "", " " not allowed
    var username : String,

    @Column(unique = true)
    @field:NotBlank
    var userId : String,

    @field:NotBlank
    var password : String,


) : BaseTimeEntity()

package com.snutaek.lugilugiserver.domain.user.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.snutaek.lugilugiserver.domain.model.BaseTimeEntity
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table
import javax.validation.constraints.NotBlank


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

    @JsonIgnore
    val authorities: String = "User",


) : BaseTimeEntity()

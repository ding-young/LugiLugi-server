package com.snutaek.lugilugiserver.domain.user.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.snutaek.lugilugiserver.domain.model.BaseTimeEntity
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank


@Entity
@Table(name = "lugi_user")
class User (

    @Column(unique = true)
    @Email
    @field:NotBlank
    var email : String,

    @field:NotBlank   // null, "", " " not allowed
    var username : String,

    @field:NotBlank
    var nickname : String,

    @field:NotBlank
    var password : String,

    @JsonIgnore
    val authorities: String = "User",


) : BaseTimeEntity()
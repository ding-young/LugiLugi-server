package com.snutaek.lugilugiserver.domain.user.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.snutaek.lugilugiserver.domain.attendance.model.Attendance
import com.snutaek.lugilugiserver.domain.group.model.Group
import com.snutaek.lugilugiserver.domain.match.model.Match
import com.snutaek.lugilugiserver.domain.model.BaseTimeEntity
import javax.persistence.*
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

    @ManyToOne
    @JoinColumn(name = "group_id", referencedColumnName = "id", nullable = true)
    var group: Group? = null,

    @Column(unique = true)
    @field:NotBlank
    val code : String,   // val ? var?

    var profileS3ObjectKey: String? = null,

    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "user")
    val attendances : List<Attendance> = listOf(),

    // TODO red, blue 어떻게 합칠까 아예 설계를 바꿔버려?
    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "red")
    val matches_red : List<Match> = listOf(),

    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "blue")
    val matches_blue : List<Match> = listOf(),
) : BaseTimeEntity()

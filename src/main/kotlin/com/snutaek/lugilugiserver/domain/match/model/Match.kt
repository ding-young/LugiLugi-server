package com.snutaek.lugilugiserver.domain.match.model


import com.snutaek.lugilugiserver.domain.model.BaseTimeEntity
import com.snutaek.lugilugiserver.domain.user.model.User
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.OneToOne
import javax.persistence.Table
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Entity
@Table(name = "lugi_match")
class Match (
    @OneToOne   // later there will be multiple judges
    var judge: User?,

    @field:NotNull
    @OneToOne
    val red: User,

    @field:NotNull
    @OneToOne
    val blue: User,

    var judgeCount: Int = 1,

    @field:NotNull
    val roundCount: Int,

    // time : 1 represents 30 sec
    @field:NotNull
    val roundTime: Int, // 2 to 6

    @field:NotNull
    val breakTime: Int,  // 1 to 2
    // match result -> another model?

    @Column(unique = true)
    @field:NotBlank
    val inviteCode : String,
) : BaseTimeEntity()
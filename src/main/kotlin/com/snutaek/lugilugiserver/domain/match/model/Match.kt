package com.snutaek.lugilugiserver.domain.match.model


import com.snutaek.lugilugiserver.domain.match.util.FlowType
import com.snutaek.lugilugiserver.domain.match.util.PlayerType
import com.snutaek.lugilugiserver.domain.model.BaseTimeEntity
import com.snutaek.lugilugiserver.domain.user.model.User
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.ManyToOne
import javax.persistence.OneToOne
import javax.persistence.Table
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Entity
@Table(name = "lugi_match")
class Match (
    @ManyToOne  // later there will be multiple judges
    var judge: User?,

    @field:NotNull
    @ManyToOne
    val red: User,

    @field:NotNull
    @ManyToOne
    val blue: User,

    var redScore: Int = 0,
    var blueScore: Int = 0,
    var redPenalty: Int = 0,   // TODO
    var bluePenalty: Int = 0,  // TODO
    var judgeCount: Int = 1,

    @field:NotNull
    val roundCount: Int,

    // time: 10 represents 10s
    @field:Min(60, message = "The roundtime must be between 60 and 180")
    @field:Max(180, message = "The roundtime must be between 60 and 180")
    @field:NotNull
    val roundTime: Int, // 60 to 180s


    @field:Min(10, message = "The breaktime must be between 10 and 60")
    @field:Max(60, message = "The breaktime must be between 10 and 60")
    @field:NotNull
    val breakTime: Int,  // 10s to 60s
    // match result -> another model?

    @Column(unique = true)
    @field:NotBlank
    val inviteCode : String,

    var flowType : FlowType = FlowType.STOP,

    var finished : Boolean = false,

    var winner : PlayerType? = null  // TODO Hmmmmmm.....

) : BaseTimeEntity()
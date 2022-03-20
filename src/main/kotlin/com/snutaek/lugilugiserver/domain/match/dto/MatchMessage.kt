package com.snutaek.lugilugiserver.domain.match.dto

import com.snutaek.lugilugiserver.domain.match.util.FlowMessageType
import com.snutaek.lugilugiserver.domain.match.util.MatchMessageType
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull


class MatchMessage {
    data class JoinMessage(
        @field:NotBlank
        val user: String,  // id or code??
        @field:NotBlank
        val inviteCode: String,
        //@field:NotBlank
        //val type: MatchMessageType
    )

    data class JudgeMessage(
        @field:NotBlank
        val judge: String,
        val player: String,  // red or blue .. or user code ?
        val score: String  // 1 2 3 -1
    )

    data class PenaltyMessage(
        @field:NotBlank
        val judge: String,
        val player: String  // red or blue .. or user code ?
    )

    data class FlowMessage(
        @field:NotBlank
        val judge: String,
        @field:NotBlank
        val type: FlowMessageType
    )
}
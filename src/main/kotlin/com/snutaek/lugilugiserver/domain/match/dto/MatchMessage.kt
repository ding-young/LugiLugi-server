package com.snutaek.lugilugiserver.domain.match.dto

import com.snutaek.lugilugiserver.domain.match.model.Match
import com.snutaek.lugilugiserver.domain.match.util.FlowMessageType
import com.snutaek.lugilugiserver.domain.match.util.MatchMessageType
import com.snutaek.lugilugiserver.domain.match.util.PlayerType
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull


class MatchMessage {
    data class JoinMessage(
        @field:NotBlank
        val userId: String,  // id
        @field:NotBlank
        val inviteCode: String,
        @field:NotBlank
        val type: MatchMessageType
    )

    data class JudgeMessage(
        @field:NotBlank
        val judge: String,
        @field:NotBlank
        val player: PlayerType,  // red or blue
        @field:NotBlank
        val score: String  // 1 2 3 -1 TODO -1 ?? to Penalty?
    )

    data class PenaltyMessage(
        @field:NotBlank
        val judge: String,
        @field:NotBlank
        val player: PlayerType  // red or blue
    )

    data class FlowMessage(
        @field:NotBlank
        val judge: String,
        @field:NotBlank
        val type: FlowMessageType
    )

    data class SimpleMessage(
        @field:NotBlank
        val content: String
    )

    data class JoinResponseMessage(
        val userId: String,  // id
        val inviteCode: String,
        val type: MatchMessageType,
        var errorType: String = "Success",
        var detail: String = "정의된 에러가 발생하지 않았습니다."
    ) {
        constructor(joinMessage: JoinMessage) : this(
            userId = joinMessage.userId,
            inviteCode = joinMessage.inviteCode,
            type = joinMessage.type,
        )
    }

    data class ScoreResponseMessage(
        val redScore: Int,
        val blueScore: Int,
        var errorType: String = "Success",
        var detail: String = "정의된 에러가 발생하지 않았습니다."
    ) {
        constructor(match: Match) : this(
            redScore = match.redScore,
            blueScore = match.blueScore
        )
    }

    data class PenaltyResponseMessage(
        val redPenalty: Int,
        val bluePenalty: Int,
        var errorType: String = "Success",
        var detail: String = "정의된 에러가 발생하지 않았습니다."
    ) {
        constructor(match: Match) : this(
            redPenalty = match.redPenalty,
            bluePenalty = match.bluePenalty
        )
    }
}
package com.snutaek.lugilugiserver.domain.match.dto

import com.snutaek.lugilugiserver.domain.match.model.Match
import com.snutaek.lugilugiserver.domain.match.util.FlowType
import com.snutaek.lugilugiserver.domain.match.util.MatchMessageType
import com.snutaek.lugilugiserver.domain.match.util.PlayerType
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

const val INVALID_SCORE = -404

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
        val flowtype: FlowType
    )

    data class SimpleMessage(
        @field:NotBlank
        val content: String
    )

    data class JoinResponseMessage(
        var userId: String = "",  // id
        var inviteCode: String = "",
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

    data class FlowResponseMessage(
        var flowtype: FlowType,
        var errorType: String = "Success",
        var detail: String = "정의된 에러가 발생하지 않았습니다." // TODO 상속 ?
    ) {
        constructor(match: Match) : this(
            flowtype = match.flowType
        )
    }

    data class ScoreResponseMessage(
        var redScore: Int = INVALID_SCORE,
        var redPenalty: Int = INVALID_SCORE,
        var blueScore: Int = INVALID_SCORE,
        var bluePenalty: Int = INVALID_SCORE,
        var errorType: String = "Success",
        var detail: String = "정의된 에러가 발생하지 않았습니다."
    ) {
        constructor(match: Match) : this(
            redScore = match.redScore,
            redPenalty = match.redPenalty,
            blueScore = match.blueScore,
            bluePenalty = match.bluePenalty,
        )
    }
}
package com.snutaek.lugilugiserver.domain.match.dto

import com.snutaek.lugilugiserver.domain.match.model.Match
import com.snutaek.lugilugiserver.domain.match.util.FlowType
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

class MatchDto {
    data class CreateMatchRequest(
        @field:NotBlank
        val redCode: String,

        @field:NotBlank
        val blueCode: String,

        val judgeCount: Int,  // judge will be determined when connection succeed

        @field:NotNull
        val roundCount: Int,

        @field:NotNull
        val roundTime: Int, // 2 to 6

        @field:NotNull
        val breakTime: Int,  // 1 to 2
        )

    data class PenaltyRequest(
        @field:NotNull
        val bluePenalty: Boolean,
        val redPenalty: Boolean
    )

    data class PenaltyResponse(
        val penalty: String
    )

    data class BaseResponse(
        val id: Long,
        val inviteCode: String,
    ) {
        constructor(match: Match) : this(
            id = match.id,
            inviteCode = match.inviteCode
        )
    }

    data class DetailResponse(
        val id: Long,
        val inviteCode: String,
        val redName: String,
        val blueName: String,
        val judgeCount: Int,
        val roundCount: Int,
        val roundTime: Int,
        val breakTime: Int,
        val flowType: FlowType,
    ) {
        constructor(match: Match) : this(
            id = match.id,
            inviteCode = match.inviteCode,
            redName = match.red.username,
            blueName = match.blue.username,
            judgeCount = match.judgeCount,
            roundCount = match.roundCount,
            roundTime = match.roundTime,
            breakTime = match.breakTime,
            flowType = match.flowType
        )
    }
}
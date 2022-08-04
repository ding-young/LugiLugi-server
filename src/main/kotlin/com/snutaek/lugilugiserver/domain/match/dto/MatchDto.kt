package com.snutaek.lugilugiserver.domain.match.dto

import com.snutaek.lugilugiserver.domain.match.model.Match
import com.snutaek.lugilugiserver.domain.match.util.FlowType
import com.snutaek.lugilugiserver.domain.match.util.PlayerType
import java.time.LocalDate
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

    data class SimpleResultResponse(
        val id: Long,
        val redName: String,
        val blueName: String,
        val redScore: Int,
        val blueScore: Int,
        val date: LocalDate?,  // Hmm..
        var win: Boolean?,  // by default
    ) {
        constructor(match: Match, username: String) : this(
            id = match.id,
            redName = match.red.username,
            blueName = match.blue.username,
            redScore = match.redScore,
            blueScore = match.blueScore,
            date = match.createdAt?.toLocalDate(),
            win = match.let { if (it.winner == null) {null}  // ties->null
            else (it.winner == PlayerType.RED && it.red.username == username) or
                (it.winner == PlayerType.BLUE && it.blue.username == username)
            } // TODO refactor
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

    data class MatchListResponse(
        val results: List<SimpleResultResponse>,
        val count: Int,
        var wins: Int = 0,
        var losses: Int = 0,
        var ties: Int = 0,  // TODO refactor
    ) {
        constructor(matchList: List<Match>, username: String) : this(
            results = matchList.map{ SimpleResultResponse(it, username) },
            count = matchList.size
        )

        fun setResultCounts() : MatchListResponse {
            results.map { if (it.win == null) ties++ else if (it.win!!) wins++ else losses++}
            return this
        }
    }
}
package com.snutaek.lugilugiserver.domain.match.api

import com.snutaek.lugilugiserver.domain.match.dto.MatchDto
import com.snutaek.lugilugiserver.domain.match.dto.MatchMessage
import com.snutaek.lugilugiserver.domain.match.service.MatchService
import com.snutaek.lugilugiserver.domain.match.util.FlowMessageType
import com.snutaek.lugilugiserver.domain.match.util.MatchMessageType
import com.snutaek.lugilugiserver.domain.match.util.PlayerType
import com.snutaek.lugilugiserver.domain.user.service.UserService
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class MatchMessageController (
    private val matchService: MatchService,
    private val userService: UserService
) {
    @MessageMapping("ws/penalty")
    @SendTo("/penalty_result")
    fun sendMessage(@Payload penaltyRequest: MatchDto.PenaltyRequest): MatchDto.PenaltyResponse? {
        if (penaltyRequest.bluePenalty) return MatchDto.PenaltyResponse("blue got penalty")
        if (penaltyRequest.redPenalty) return MatchDto.PenaltyResponse("red got penalty")
        return MatchDto.PenaltyResponse("nobody got penalty")
    }

    @MessageMapping("ws/create")
    @SendTo("/subscribe/{inviteCode}")
    fun createMatchRoom(@Payload joinMessage: MatchMessage.JoinMessage, @PathVariable inviteCode:String): MatchMessage.JoinMessage {
        return joinMessage
    }

    @MessageMapping("/{inviteCode}/join")
    @SendTo("/subscribe/{inviteCode}")
    fun joinMatchRoom(@Payload joinMessage: MatchMessage.JoinMessage, @PathVariable inviteCode:String): MatchMessage.JoinMessage {
        if (joinMessage.type == MatchMessageType.JUDGE) {
            val judge = userService.findById(joinMessage.userId.toLong())
            matchService.assignJudge(judge, joinMessage.inviteCode)  // how to check?
        }
        return joinMessage
    }

    @MessageMapping("/{inviteCode}/flow")
    @SendTo("/subscribe/{inviteCode}")
    fun controlMatchFlow(@Payload flowMessage: MatchMessage.FlowMessage, @PathVariable inviteCode:String): MatchMessage.FlowMessage {
        return flowMessage
    }

    @MessageMapping("/{inviteCode}/score")
    @SendTo("/subscribe/{inviteCode}")
    fun scoreMatch(@Payload judgeMessage: MatchMessage.JudgeMessage, @PathVariable inviteCode:String): MatchMessage.ScoreResponseMessage {
        var match = matchService.findByInviteCode(inviteCode)
        if (judgeMessage.player == PlayerType.RED) {
            match = matchService.scoreRed(match, judgeMessage.score.toInt())
        } else {
            match = matchService.scoreBlue(match, judgeMessage.score.toInt())
        }
        return MatchMessage.ScoreResponseMessage(match)
    }

    @MessageMapping("ping/")
    @SendTo("/subscribe/pong/")
    fun wsPing(): String {
        return "ws pong"
    }

}

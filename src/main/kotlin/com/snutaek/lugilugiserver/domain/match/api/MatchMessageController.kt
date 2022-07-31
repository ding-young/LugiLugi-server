package com.snutaek.lugilugiserver.domain.match.api

import com.snutaek.lugilugiserver.domain.match.dto.MatchDto
import com.snutaek.lugilugiserver.domain.match.dto.MatchMessage
import com.snutaek.lugilugiserver.domain.match.service.MatchService
import com.snutaek.lugilugiserver.domain.match.util.FlowMessageType
import com.snutaek.lugilugiserver.domain.match.util.MatchMessageType
import com.snutaek.lugilugiserver.domain.match.util.PlayerType
import com.snutaek.lugilugiserver.domain.user.exception.UserNotFoundException
import com.snutaek.lugilugiserver.domain.user.service.UserService
import org.springframework.messaging.handler.annotation.DestinationVariable
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
    @MessageMapping("/{inviteCode}/join")
    @SendTo("/subscribe/{inviteCode}")
    fun joinMatchRoom(@Payload joinMessage: MatchMessage.JoinMessage, @DestinationVariable inviteCode:String): MatchMessage.JoinResponseMessage {
        if (joinMessage.type == MatchMessageType.JUDGE) {
            val judge = try {
                userService.findById(joinMessage.userId.toLong())
            } catch (e: UserNotFoundException) {
                return MatchMessage.JoinResponseMessage(type = MatchMessageType.JUDGE, errorType = "USER_NOT_FOUND", detail = "{${joinMessage.userId}에 해당하는 user가 없습니다.}")
            }
            matchService.assignJudge(judge, joinMessage.inviteCode)  // how to check?
        }
        return MatchMessage.JoinResponseMessage(joinMessage)
    }

    @MessageMapping("/{inviteCode}/flow")
    @SendTo("/subscribe/{inviteCode}")
    fun controlMatchFlow(@Payload flowMessage: MatchMessage.FlowMessage, @DestinationVariable inviteCode:String): MatchMessage.SimpleMessage {
        return MatchMessage.SimpleMessage("flow") // flowMessage
    }

    @MessageMapping("/{inviteCode}/score")
    @SendTo("/subscribe/{inviteCode}")
    fun scoreMatch(@Payload judgeMessage: MatchMessage.JudgeMessage, @DestinationVariable inviteCode:String): MatchMessage.ScoreResponseMessage {
        var match = matchService.findByInviteCode(inviteCode)
            ?: return MatchMessage.ScoreResponseMessage(errorType = "MATCH_NOT_FOUND", detail ="{$inviteCode} 에 해당하는 match 가 없습니다.")
        if (judgeMessage.player == PlayerType.RED) {
            match = matchService.scoreRed(match, judgeMessage.score.toInt())
        } else {
            match = matchService.scoreBlue(match, judgeMessage.score.toInt())
        }
        return MatchMessage.ScoreResponseMessage(match)
    }

    // Score랑 penalty를 별도로 두어야 할 이유? 다른 로직으로 처리하게 되나?? 더 고민 상대한테 일점이 올라감
    @MessageMapping("/{inviteCode}/penalty")
    @SendTo("/subscribe/{inviteCode}")
    fun penaltyMatch(@Payload penaltyMessage: MatchMessage.PenaltyMessage, @DestinationVariable inviteCode:String): MatchMessage.ScoreResponseMessage {
        var match = matchService.findByInviteCode(inviteCode)
            ?: return MatchMessage.ScoreResponseMessage(errorType = "MATCH_NOT_FOUND", detail = "{$inviteCode} 에 해당하는 match 가 없습니다.")
        if (penaltyMessage.player == PlayerType.RED) {
            match = matchService.penaltyRed(match)
        } else {
            match = matchService.penaltyBlue(match)
        }
        return MatchMessage.ScoreResponseMessage(match)
    }

    @MessageMapping("/ping")
    @SendTo("/subscribe/pong")
    fun wsPing(): MatchMessage.SimpleMessage {
        return MatchMessage.SimpleMessage("pong")
    }

}

package com.snutaek.lugilugiserver.domain.match.api

import com.snutaek.lugilugiserver.domain.match.dto.MatchDto
import com.snutaek.lugilugiserver.domain.match.dto.MatchMessage
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
@MessageMapping("/match")
class MatchMessageController (
) {
    @MessageMapping("/penalty")
    @SendTo("/penalty_result")
    fun sendMessage(@Payload penaltyRequest: MatchDto.PenaltyRequest): MatchDto.PenaltyResponse? {
        if (penaltyRequest.bluePenalty) return MatchDto.PenaltyResponse("blue got penalty")
        if (penaltyRequest.redPenalty) return MatchDto.PenaltyResponse("red got penalty")
        return MatchDto.PenaltyResponse("nobody got penalty")
    }

    @MessageMapping("/create")
    @SendTo("/subscribe/public")
    fun createMatchRoom(@Payload joinMessage: MatchMessage.JoinMessage): MatchMessage.JoinMessage {
        return joinMessage
    }

    @MessageMapping("/{inviteCode}/join")
    @SendTo("/subscribe/{inviteCode}")
    fun joinMatchRoom(@Payload joinMessage: MatchMessage.JoinMessage, @PathVariable inviteCode:String): MatchMessage.JoinMessage {
        return joinMessage
    }

}

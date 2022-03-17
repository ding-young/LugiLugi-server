package com.snutaek.lugilugiserver.domain.match.api

import com.snutaek.lugilugiserver.domain.match.dto.MatchDto
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.web.bind.annotation.RestController

@RestController
class MatchMessageController (
) {
    @MessageMapping("/match/penalty")
    @SendTo("/match/penalty_result")
    fun sendMessage(@Payload penaltyRequest: MatchDto.PenaltyRequest): MatchDto.PenaltyResponse? {
        if (penaltyRequest.bluePenalty) return MatchDto.PenaltyResponse("blue got penalty")
        if (penaltyRequest.redPenalty) return MatchDto.PenaltyResponse("red got penalty")
        return MatchDto.PenaltyResponse("nobody got penalty")
    }
}

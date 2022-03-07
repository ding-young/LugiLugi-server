package com.snutaek.lugilugiserver.domain.match.api

import com.snutaek.lugilugiserver.domain.match.dto.GroupDto
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class MatchController (
) {
    @MessageMapping("/match/penalty")
    @SendTo("/match/penalty_result")
    fun sendMessage(@Payload penaltyRequest: GroupDto.PenaltyRequest): GroupDto.PenaltyResponse? {
        if (penaltyRequest.blue_penalty) return GroupDto.PenaltyResponse("blue got penalty")
        if (penaltyRequest.red_penalty) return GroupDto.PenaltyResponse("red got penalty")
        return GroupDto.PenaltyResponse("nobody got penalty")
    }
}

package com.snutaek.lugilugiserver.domain.match.api

import com.snutaek.lugilugiserver.domain.group.dto.GroupDto
import com.snutaek.lugilugiserver.domain.match.dto.MatchDto
import com.snutaek.lugilugiserver.domain.match.exception.MatchNotFoundException
import com.snutaek.lugilugiserver.domain.match.service.MatchService
import com.snutaek.lugilugiserver.domain.user.service.UserService
import com.snutaek.lugilugiserver.global.common.dto.ListResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RequestMapping("api/v1/match")
@RestController
class MatchController (
    private val matchService: MatchService,
    private val userService: UserService
) {
    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    fun createMatch(@RequestBody createMatchRequest: MatchDto.CreateMatchRequest) : MatchDto.BaseResponse {
        val match = matchService.createMatch(createMatchRequest)
        return MatchDto.BaseResponse(match)
    }

    // 이거 안 쓰나 ??
    @GetMapping("/{matchId}/")
    fun getMatch(@PathVariable matchId: Long) : MatchDto.DetailResponse {
        val match = matchService.findById(matchId)
        return MatchDto.DetailResponse(match)
    }

    // api for debugging.. 였는데..
    @GetMapping("/inviteCode/{inviteCode}/")
    fun getMatch(@PathVariable inviteCode: String) : MatchDto.DetailResponse {
        val match = matchService.findByInviteCode(inviteCode) ?: throw MatchNotFoundException("해당하는 match 못찾음")
        return MatchDto.DetailResponse(match)
    }

    // 이 이후에 경기 기록 자체를 불변하게 할 수 있나? 그래야 하나 ?
    @PostMapping("/inviteCode/{inviteCode}/finish/")
    fun finishMatch(@PathVariable inviteCode: String) : MatchDto.DetailResponse {
        val match = matchService.findByInviteCode(inviteCode) ?: throw MatchNotFoundException("해당하는 match 못찾음")
        return MatchDto.DetailResponse(matchService.finishMatch(match))
    }

    @GetMapping("/record/{userId}/")
    fun getUserMatchRecords(@PathVariable userId: Long) : MatchDto.MatchListResponse {
        val user = userService.findById(userId)
        val matches = user.matches_red + user.matches_blue
        val finishedMatches = matches.filter { it.finished }.sortedBy { it.createdAt }
        val responses = MatchDto.MatchListResponse(finishedMatches, user.username)
        return responses.setResultCounts()
    }
}
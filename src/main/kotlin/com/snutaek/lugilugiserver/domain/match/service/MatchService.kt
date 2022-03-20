package com.snutaek.lugilugiserver.domain.match.service

import com.snutaek.lugilugiserver.domain.group.dto.GroupDto
import com.snutaek.lugilugiserver.domain.group.exception.GroupNameAlreadyExistsException
import com.snutaek.lugilugiserver.domain.group.exception.GroupNotFoundException
import com.snutaek.lugilugiserver.domain.group.model.Group
import com.snutaek.lugilugiserver.domain.match.dto.MatchDto
import com.snutaek.lugilugiserver.domain.match.exception.MatchNotFoundException
import com.snutaek.lugilugiserver.domain.match.model.Match
import com.snutaek.lugilugiserver.domain.match.repository.MatchRepository
import com.snutaek.lugilugiserver.domain.match.util.RandomInviteCodeGenerator
import com.snutaek.lugilugiserver.domain.user.exception.UserNotFoundException
import com.snutaek.lugilugiserver.domain.user.model.User
import com.snutaek.lugilugiserver.domain.user.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class MatchService (
    private val matchRepository : MatchRepository,
    private val userRepository : UserRepository,
    private val randomInviteCodeGenerator: RandomInviteCodeGenerator
) {
    fun createMatch(createMatchRequest: MatchDto.CreateMatchRequest) : Match {
        val red = userRepository.findByCode(createMatchRequest.redCode)
        val blue = userRepository.findByCode(createMatchRequest.blueCode)
        if (red==null || blue==null) throw UserNotFoundException("존재하지 않는 유저입니다")  // TODO separate error message..?
        val inviteCode = createMatchInviteCode()
        return matchRepository.save(Match(judge = null, red = red, blue = blue, judgeCount = createMatchRequest.judgeCount, roundCount = createMatchRequest.roundCount, roundTime = createMatchRequest.roundTime, breakTime = createMatchRequest.breakTime, inviteCode = inviteCode))
    }

    fun createMatchInviteCode() : String {
        val inviteCode = randomInviteCodeGenerator.generateRandomUniqueUserCode()
        if (matchRepository.existsByInviteCode(inviteCode)) return createMatchInviteCode()
        return inviteCode
    }

    fun findById(id: Long) : Match {
        return matchRepository.findByIdOrNull(id) ?: throw MatchNotFoundException("Match $id 에 해당하는 Match는 존재하지 않습니다")
    }

    fun findByInviteCode(inviteCode: String) : Match {
        return matchRepository.findMatchByInviteCode(inviteCode) ?: throw MatchNotFoundException("Match $inviteCode 에 해당하는 Match는 존재하지 않습니다")
    }

    fun assignJudge(judge: User, inviteCode: String) : Unit {
        val match = findByInviteCode(inviteCode)
        match.judge = judge
        matchRepository.save(match)
    }

    fun scoreRed(match: Match, score: Int) : Match {
        match.redScore += score
        return matchRepository.save(match)
    }

    fun scoreBlue(match: Match, score: Int) : Match {
        match.blueScore += score
        return matchRepository.save(match)
    }
}
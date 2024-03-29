package com.snutaek.lugilugiserver.domain.match.service

import com.snutaek.lugilugiserver.domain.group.dto.GroupDto
import com.snutaek.lugilugiserver.domain.group.exception.GroupNameAlreadyExistsException
import com.snutaek.lugilugiserver.domain.group.exception.GroupNotFoundException
import com.snutaek.lugilugiserver.domain.group.model.Group
import com.snutaek.lugilugiserver.domain.match.dto.MatchDto
import com.snutaek.lugilugiserver.domain.match.exception.MatchNotFoundException
import com.snutaek.lugilugiserver.domain.match.model.Match
import com.snutaek.lugilugiserver.domain.match.repository.MatchRepository
import com.snutaek.lugilugiserver.domain.match.util.FlowType
import com.snutaek.lugilugiserver.domain.match.util.PlayerType
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

    fun findByInviteCode(inviteCode: String) : Match? {
        return matchRepository.findMatchByInviteCode(inviteCode) // throw MatchNotFoundException("Match $inviteCode 에 해당하는 Match는 존재하지 않습니다")
    }

    fun assignJudge(judge: User, inviteCode: String) : Unit {
        val match = findByInviteCode(inviteCode) ?: return
        match.judge = judge
        matchRepository.save(match)
    }

    fun controlFlow(match: Match, flowStatus: FlowType) : Match {
        match.flowType = flowStatus
        return matchRepository.save(match)
    }

    fun scoreRed(match: Match, score: Int) : Match {
        match.redScore += score
        return matchRepository.save(match)
    }

    fun scoreBlue(match: Match, score: Int) : Match {
        match.blueScore += score
        return matchRepository.save(match)
    }

    // 사실 score 나 penalty 나 같은 걸로 처리할 수 있음 근데 음수로 저장할지, 점수 매기기나 감점 이루어질 때 트리거 되는 ?
    // 체크하는 로직이 어케 달라지는 지 고민 좀
    fun penaltyRed(match: Match) : Match {
        match.redPenalty += 1
        match.blueScore += 1
        return matchRepository.save(match)
    }

    fun penaltyBlue(match: Match) : Match {
        match.bluePenalty += 1
        match.redScore += 1
        return matchRepository.save(match)
    }

    fun finishMatch(match: Match) : Match {
        match.finished = true
        // 코드 끔찍함 .. 공부 제대로 하고 바꾸기
        match.winner = if (match.redScore > match.blueScore) {PlayerType.RED}
        else if (match.redScore < match.blueScore) {PlayerType.BLUE}
        else {null}
        return matchRepository.save(match)
    }
}
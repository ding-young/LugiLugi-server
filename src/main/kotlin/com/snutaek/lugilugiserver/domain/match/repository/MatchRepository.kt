package com.snutaek.lugilugiserver.domain.match.repository

import com.snutaek.lugilugiserver.domain.match.model.Match
import com.snutaek.lugilugiserver.domain.user.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface MatchRepository : JpaRepository<Match, Long> {
    fun findMatchByBlue(blue: User) : Match?
    fun findMatchByRed(red: User) : Match?
    fun findMatchByInviteCode(inviteCode: String) : Match?
    fun existsByInviteCode(inviteCode: String) : Boolean
}
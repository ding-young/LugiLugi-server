package com.snutaek.lugilugiserver.domain.match.util

import org.springframework.stereotype.Component

@Component
class RandomInviteCodeGenerator {  // TODO refactor
    private val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
    private val stringLength: Long = 8

    fun generateRandomUniqueUserCode() : String {
        val randomString = (1..stringLength)
            .map { i -> kotlin.random.Random.nextInt(0, charPool.size) }
            .map(charPool::get)
            .joinToString("");
        return randomString
    }
}
package com.snutaek.lugilugiserver.domain.user.util

import com.snutaek.lugilugiserver.domain.user.repository.UserRepository
import org.springframework.stereotype.Component

@Component
class RandomCodeGenerator {
    private val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
    private val stringLength: Long = 6

    fun generateRandomUniqueUserCode() : String {
        val randomString = (1..stringLength)
            .map { i -> kotlin.random.Random.nextInt(0, charPool.size) }
            .map(charPool::get)
            .joinToString("");
        return randomString
    }
}
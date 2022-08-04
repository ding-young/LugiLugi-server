package com.snutaek.lugilugiserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
class LugiLugiServerApplication

fun main(args: Array<String>) {
    runApplication<LugiLugiServerApplication>(*args)
}

package com.snutaek.lugilugiserver.global.common.exception

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class LugiControllerAdvice {
    private val logger = LoggerFactory.getLogger(this.javaClass.name)

    @ExceptionHandler(value = [DataNotFoundException::class])
    fun notFound(e: LugiException) =
        ResponseEntity(ErrorResponse(e.errorType.code, e.errorType.name, e.detail), HttpStatus.NOT_FOUND)

    @ExceptionHandler(value = [InvalidRequestException::class])
    fun badRequest(e: LugiException) =
        ResponseEntity(ErrorResponse(e.errorType.code, e.errorType.name, e.detail), HttpStatus.BAD_REQUEST)

    @ExceptionHandler(value = [NotAllowedException::class])
    fun notAllowed(e: LugiException) =
        ResponseEntity(ErrorResponse(e.errorType.code, e.errorType.name, e.detail), HttpStatus.FORBIDDEN)

    @ExceptionHandler(value = [ConflictException::class])
    fun conflict(e: LugiException) =
        ResponseEntity(ErrorResponse(e.errorType.code, e.errorType.name, e.detail), HttpStatus.CONFLICT)
}
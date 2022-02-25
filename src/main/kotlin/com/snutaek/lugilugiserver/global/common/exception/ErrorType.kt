package com.snutaek.lugilugiserver.global.common.exception

enum class ErrorType (
    val code: Int,
) {
    INVALID_REQUEST(0),

    NOT_ALLOWED(3000),

    DATA_NOT_FOUND(4000),
    USER_NOT_FOUND(4001),

    CONFLICT(9000),
    USER_EMAIL_ALREADY_EXISTS(9001),

    SERVER_ERROR(10000)
}
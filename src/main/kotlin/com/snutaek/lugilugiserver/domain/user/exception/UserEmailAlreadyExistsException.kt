package com.snutaek.lugilugiserver.domain.user.exception

import com.snutaek.lugilugiserver.global.common.exception.ConflictException
import com.snutaek.lugilugiserver.global.common.exception.ErrorType

class UserEmailAlreadyExistsException(detail: String = "")
    : ConflictException(ErrorType.USER_EMAIL_ALREADY_EXISTS, detail)
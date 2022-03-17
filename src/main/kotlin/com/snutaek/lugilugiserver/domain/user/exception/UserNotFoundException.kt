package com.snutaek.lugilugiserver.domain.user.exception

import com.snutaek.lugilugiserver.global.common.exception.DataNotFoundException
import com.snutaek.lugilugiserver.global.common.exception.ErrorType

class UserNotFoundException (detail: String = "")
    : DataNotFoundException(ErrorType.USER_NOT_FOUND)
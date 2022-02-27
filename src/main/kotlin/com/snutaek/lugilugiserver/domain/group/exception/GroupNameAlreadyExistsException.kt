package com.snutaek.lugilugiserver.domain.group.exception

import com.snutaek.lugilugiserver.global.common.exception.ConflictException
import com.snutaek.lugilugiserver.global.common.exception.ErrorType

class GroupNameAlreadyExistsException (detail: String = "")
    : ConflictException(ErrorType.GROUP_NAME_ALREADY_EXISTS)
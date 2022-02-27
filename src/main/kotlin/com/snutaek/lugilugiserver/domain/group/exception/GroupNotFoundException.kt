package com.snutaek.lugilugiserver.domain.group.exception

import com.snutaek.lugilugiserver.global.common.exception.DataNotFoundException
import com.snutaek.lugilugiserver.global.common.exception.ErrorType

class GroupNotFoundException (detail: String = "")
    : DataNotFoundException(ErrorType.GROUP_NOT_FOUND)
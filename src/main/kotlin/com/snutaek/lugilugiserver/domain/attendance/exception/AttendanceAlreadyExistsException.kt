package com.snutaek.lugilugiserver.domain.attendance.exception

import com.snutaek.lugilugiserver.global.common.exception.ConflictException
import com.snutaek.lugilugiserver.global.common.exception.ErrorType

class AttendanceAlreadyExistsException (detail: String = "")
    : ConflictException(ErrorType.ATTENDANCE_ALREADY_EXISTS)


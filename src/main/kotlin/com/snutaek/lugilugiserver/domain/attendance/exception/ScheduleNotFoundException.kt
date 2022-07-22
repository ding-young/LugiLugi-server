package com.snutaek.lugilugiserver.domain.attendance.exception

import com.snutaek.lugilugiserver.global.common.exception.DataNotFoundException
import com.snutaek.lugilugiserver.global.common.exception.ErrorType

class ScheduleNotFoundException (detail: String = "")
    : DataNotFoundException(ErrorType.SCHEDULE_NOT_FOUND)
package com.snutaek.lugilugiserver.domain.attendance.dto

import com.snutaek.lugilugiserver.domain.group.model.Attendance
import com.snutaek.lugilugiserver.domain.user.dto.UserDto
import java.time.LocalDate
import javax.validation.constraints.NotBlank

class AttendanceDto {
    data class MarkAttendanceRequest(
        @field:NotBlank
        val date: LocalDate, // 'yyyy-mm-dd'
    )

    data class BaseResponse(
        val username: String,
        val date: LocalDate,
    ) {
        constructor(attendance: Attendance) : this(
            username = attendance.user.username,
            date = attendance.schedule.date
        )
    }
}
package com.snutaek.lugilugiserver.domain.attendance.api

import com.snutaek.lugilugiserver.domain.attendance.dto.AttendanceDto
import com.snutaek.lugilugiserver.domain.attendance.service.AttendanceService
import com.snutaek.lugilugiserver.domain.user.dto.UserDto
import com.snutaek.lugilugiserver.domain.user.model.User
import com.snutaek.lugilugiserver.global.auth.CurrentUser
import com.snutaek.lugilugiserver.global.common.dto.ListResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.time.*

@RestController
@RequestMapping("api/v1/attendance")
class AttendanceController (
    private val attendanceService: AttendanceService,
) {
    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    fun markAttendance(
        @CurrentUser user: User,
        @RequestBody markAttendanceRequest: AttendanceDto.MarkAttendanceRequest
    ) : AttendanceDto.BaseResponse {
        val schedule = attendanceService.findByDateOrCreate(markAttendanceRequest.date)  // find or create schedule
        val attendance = attendanceService.markAttendance(user, schedule)
        return AttendanceDto.BaseResponse(attendance)
    }

    @GetMapping("/me/")
    fun getMyAttendance(
        @CurrentUser user: User, // Q. 다른 사람의 월별 출석 기록 알 일이 있나?
        @RequestParam(value="year", required = true) year: Year,
        @RequestParam(value="month", required = true) month: Int,
    ) : ListResponse<LocalDate> {
        val dates = attendanceService.getUserAttendanceInMonth(user, year, Month.of(month))
        return ListResponse(dates)
    }

    @GetMapping("/")
    fun getAttendants(
        @CurrentUser user: User,
        @RequestParam(value="year", required = true) year: Int,
        @RequestParam(value="month", required = true) month: Int,
        @RequestParam(value="day", required = true) day: Int,
    ) : ListResponse<UserDto.BaseResponse> {
        val users = attendanceService.getAllAttendantsInDay(LocalDate.of(year, month, day))
        return ListResponse(users.map { UserDto.BaseResponse(it) })
    }
}
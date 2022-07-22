package com.snutaek.lugilugiserver.domain.group.service

import com.snutaek.lugilugiserver.domain.attendance.exception.ScheduleNotFoundException
import com.snutaek.lugilugiserver.domain.attendance.model.Schedule
import com.snutaek.lugilugiserver.domain.attendance.repository.ScheduleRepository
import com.snutaek.lugilugiserver.domain.group.dto.GroupDto
import com.snutaek.lugilugiserver.domain.group.exception.GroupNameAlreadyExistsException
import com.snutaek.lugilugiserver.domain.group.exception.GroupNotFoundException
import com.snutaek.lugilugiserver.domain.group.model.Attendance
import com.snutaek.lugilugiserver.domain.group.repository.AttendanceRepository
import com.snutaek.lugilugiserver.domain.user.exception.UserNotFoundException
import com.snutaek.lugilugiserver.domain.user.model.User
import com.snutaek.lugilugiserver.domain.user.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.*

@Service
class AttendanceService (
    private val scheduleRepository: ScheduleRepository,
    private val userRepository: UserRepository,
    private val attendanceRepository: AttendanceRepository,
) {
    fun findByDateOrCreate(date: LocalDate) : Schedule {
        return scheduleRepository.findByDate(date) ?: scheduleRepository.save(Schedule(date))
    }

    fun markAttendance(user: User, schedule: Schedule) : Attendance {
        return attendanceRepository.save(Attendance(user, schedule))
    }

    fun getUserAttendanceInMonth(user: User, year: Year, month: Month) : List<LocalDate> {
        val startOfMonth = LocalDate.of(year.value, month, 1)
        val endOfMonth = LocalDate.of(year.value, month, month.length(year.isLeap))
        return scheduleRepository.getScheduleByUserAndDateBetween(user.id, startOfMonth, endOfMonth)
    }

    fun getAllAttendantsInDay(date: LocalDate) : List<User> {
        val schedule = scheduleRepository.findByDate(date)
            ?: throw throw ScheduleNotFoundException("Schedule $date 에 해당하는 Schedule은 존재하지 않습니다")
        return schedule.attendances.map { attendance -> attendance.user }
    }
}
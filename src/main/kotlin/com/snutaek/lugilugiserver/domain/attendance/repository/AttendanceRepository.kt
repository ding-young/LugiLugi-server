package com.snutaek.lugilugiserver.domain.attendance.repository

import com.snutaek.lugilugiserver.domain.attendance.model.Schedule
import com.snutaek.lugilugiserver.domain.attendance.model.Attendance
import com.snutaek.lugilugiserver.domain.user.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface AttendanceRepository : JpaRepository<Attendance, Long>{
    fun findAllBySchedule(schedule: Schedule) : List<Attendance>?
    fun existsByUserAndSchedule(user: User, schedule: Schedule) : Boolean
}
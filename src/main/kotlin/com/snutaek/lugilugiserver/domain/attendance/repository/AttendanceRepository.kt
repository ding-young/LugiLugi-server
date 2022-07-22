package com.snutaek.lugilugiserver.domain.group.repository

import com.snutaek.lugilugiserver.domain.attendance.model.Schedule
import com.snutaek.lugilugiserver.domain.group.model.Attendance
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface AttendanceRepository : JpaRepository<Attendance, Long>{
    fun findByName(name: String) : Attendance?
    fun existsByName(name: String) : Boolean
    fun findAllBySchedule(schedule: Schedule) : List<Attendance>?


}
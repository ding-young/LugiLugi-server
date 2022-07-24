package com.snutaek.lugilugiserver.domain.attendance.repository

import com.snutaek.lugilugiserver.domain.attendance.model.Schedule
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.LocalDate

interface ScheduleRepository : JpaRepository<Schedule, Long> {
    fun findByDate(date: LocalDate): Schedule?
    fun findAllByDateBetween(start: LocalDate, end: LocalDate): List<Schedule>?

    @Query(
        "SELECT s.date FROM Attendance a left join Schedule s ON a.schedule.id = s.id " +
                "WHERE (a.user.id = :userId) AND (s.date BETWEEN :start AND :end) "
    )
    fun getScheduleByUserAndDateBetween(
        @Param("userId") userId: Long,
        @Param("start") start: LocalDate,
        @Param("end") end: LocalDate
    ): List<LocalDate>

}
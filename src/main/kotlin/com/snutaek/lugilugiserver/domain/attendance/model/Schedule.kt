package com.snutaek.lugilugiserver.domain.attendance.model

import com.snutaek.lugilugiserver.domain.group.model.Attendance
import com.snutaek.lugilugiserver.domain.model.BaseTimeEntity
import java.time.LocalDate
import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Entity
@Table(name = "schedule")
class Schedule (
    @field:NotNull
    @Column(unique = true)
    var date: LocalDate,

    @field:NotBlank
    var info : String = "정기운동",

    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "schedule")
    val attendances : List<Attendance> = listOf()

    ) : BaseTimeEntity()
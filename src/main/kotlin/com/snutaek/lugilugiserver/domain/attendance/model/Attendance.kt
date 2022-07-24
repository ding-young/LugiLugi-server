package com.snutaek.lugilugiserver.domain.attendance.model

import com.snutaek.lugilugiserver.domain.attendance.model.Schedule
import com.snutaek.lugilugiserver.domain.model.BaseTimeEntity
import com.snutaek.lugilugiserver.domain.user.model.User
import javax.persistence.*

@Entity
@Table(name = "attendance")  // user_schedule mapping table
class Attendance (
    @ManyToOne(fetch = FetchType.LAZY)
    val user: User,

    @ManyToOne(fetch = FetchType.LAZY)
    val schedule: Schedule,

    ) : BaseTimeEntity()
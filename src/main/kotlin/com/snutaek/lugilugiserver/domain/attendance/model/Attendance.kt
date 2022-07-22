package com.snutaek.lugilugiserver.domain.group.model

import com.snutaek.lugilugiserver.domain.attendance.model.Schedule
import com.snutaek.lugilugiserver.domain.model.BaseTimeEntity
import com.snutaek.lugilugiserver.domain.user.model.User
import javax.persistence.*

@Entity
@Table(name = "attendance")  // user_schedule mapping table
class Attendance (
    @ManyToOne(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val user: User,

    @ManyToOne(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val schedule: Schedule,

    ) : BaseTimeEntity()
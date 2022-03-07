package com.snutaek.lugilugiserver.domain.match.model


import com.snutaek.lugilugiserver.domain.model.BaseTimeEntity
import com.snutaek.lugilugiserver.domain.user.model.User
import javax.persistence.Entity
import javax.persistence.OneToOne

@Entity
class Match (

    @OneToOne   // later there will be multiple judges
    val judge: User,

    @OneToOne
    val red: User,

    @OneToOne
    val blue: User,

    var red_win: Boolean
) : BaseTimeEntity()
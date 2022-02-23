package com.snutaek.lugilugiserver.domain.model

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import javax.persistence.EntityListeners
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.MappedSuperclass

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
open class BaseTimeEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @CreatedDate
    var createdAt: LocalDateTime? = null,

    @LastModifiedDate
    var updatedAt: LocalDateTime? = null
)

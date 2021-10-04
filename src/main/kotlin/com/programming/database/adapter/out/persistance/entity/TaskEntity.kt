package com.programming.database.adapter.out.persistance.entity

import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "tasks")
data class TaskEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null,
    val title: String,
    val description: String? = null,
    val startDate: Date? = null,
    val dueDate: Date? = null,
    val status: Int,
    val priority: Int,
    @Column(name = "created_at")
    val createdAt: LocalDateTime? = LocalDateTime.now(),
    @Column(name = "updated_at")
    val updatedAt: LocalDateTime? = LocalDateTime.now(),
)
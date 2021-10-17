package com.programming.database.adapter.out.persistance.entity

import java.time.OffsetDateTime
import javax.persistence.*

@Entity
@Table(name = "tasks")
data class TaskEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null,
    val title: String,
    val description: String? = null,
    @Column(name = "start_date")
    val startDate: OffsetDateTime? = null,
    @Column(name = "due_date")
    val dueDate: OffsetDateTime? = null,
    val status: Int,
    val priority: Int,
    @Column(name = "created_at")
    val createdAt: OffsetDateTime? = OffsetDateTime.now(),
    @Column(name = "updated_at")
    val updatedAt: OffsetDateTime? = OffsetDateTime.now(),
)
package com.programming.database.domain

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming
import java.time.LocalDateTime
import java.util.*

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
data class Task(
    val id: Long,
    val title: String,
    val description: String? = null,
    val startDate: Date? = null,
    val dueDate: Date? = null,
    val status: Int,
    val priority: Int,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)
package com.programming.database.adapter.`in`.controller.dto

import org.springframework.format.annotation.DateTimeFormat
import java.time.OffsetDateTime

data class TaskDTO(
    val title: String,
    val description: String? = null,
    val startDate: OffsetDateTime? = null,
    val dueDate: OffsetDateTime? = null,
    val status: Int,
    val priority: Int
)

data class TaskFiltersDTO(
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    val startDate: OffsetDateTime?,
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    val endDate: OffsetDateTime?,
)
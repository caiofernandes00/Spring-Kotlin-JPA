package com.programming.database.adapter.`in`.controller.dto

import org.springframework.format.annotation.DateTimeFormat
import java.util.*

data class TaskDTO(
    val title: String,
    val description: String? = null,
    val startDate: Date? = null,
    val dueDate: Date? = null,
    val status: Int,
    val priority: Int
)

data class TaskFiltersDTO(
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    val startDate: Date?,
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    val endDate: Date?,
)
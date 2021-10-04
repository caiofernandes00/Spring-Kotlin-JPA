package com.programming.database.adapter.`in`.controller.dto

import java.util.*

data class TaskDTO(
    val title: String,
    val description: String? = null,
    val startDate: Date? = null,
    val dueDate: Date? = null,
    val status: Int,
    val priority: Int
)
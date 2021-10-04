package com.programming.database.adapter.out.persistance.mapper

import com.programming.database.adapter.`in`.controller.dto.TaskDTO
import com.programming.database.domain.Task
import com.programming.database.adapter.out.persistance.entity.TaskEntity

fun TaskDTO.toEntity() = TaskEntity(
    description = description,
    dueDate = dueDate,
    priority = priority,
    startDate = startDate,
    status = status,
    title = title
)

fun TaskEntity.toDomain() = Task(
    id = id!!,
    title = title,
    description = description,
    dueDate = dueDate,
    status = status,
    startDate = startDate,
    priority = priority,
    createdAt = createdAt!!,
    updatedAt = updatedAt!!
)
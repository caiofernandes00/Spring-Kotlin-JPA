package com.programming.database.application.port.out

import com.programming.database.adapter.`in`.controller.dto.TaskDTO
import com.programming.database.domain.Task
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.util.*

interface TaskRepositoryPort {
    fun getTasks(pageable: Pageable, startDate: Date?, endDate: Date?): Page<Task>
    fun addTask(task: TaskDTO): Task
    fun getTaskById(taskId: Long): Task?
    fun putTask(taskId: Long, newTask: TaskDTO): Task?
    fun deleteTask(taskId: Long)
}
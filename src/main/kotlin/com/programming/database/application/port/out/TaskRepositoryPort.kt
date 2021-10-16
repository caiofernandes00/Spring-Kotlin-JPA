package com.programming.database.application.port.out

import com.programming.database.adapter.`in`.controller.dto.TaskDTO
import com.programming.database.adapter.`in`.controller.dto.TaskFiltersDTO
import com.programming.database.domain.Task
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface TaskRepositoryPort {
    fun getTasks(pageable: Pageable, fieldsRequest: TaskFiltersDTO): Page<Task>
    fun addTask(task: TaskDTO): Task
    fun getTaskById(taskId: Long): Task?
    fun putTask(taskId: Long, newTask: TaskDTO): Task?
    fun deleteTask(taskId: Long)
}
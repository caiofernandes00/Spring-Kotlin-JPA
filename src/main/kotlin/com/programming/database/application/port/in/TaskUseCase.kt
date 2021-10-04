package com.programming.database.application.port.`in`

import com.programming.database.adapter.`in`.controller.dto.TaskDTO
import com.programming.database.domain.Task

interface TaskUseCase {
    fun getTasks(): List<Task>
    fun addTask(task: TaskDTO): Task
    fun getTaskById(taskId: String): Task?
    fun putTask(taskId: String, newTask: TaskDTO): Task?
    fun deleteTask(taskId: String)
}
package com.programming.database.application.port.out

import com.programming.database.adapter.`in`.controller.dto.TaskDTO
import com.programming.database.domain.Task

interface TaskRepositoryAdapter {
    fun getTasks(): List<Task>
    fun addTask(task: TaskDTO): Task
    fun getTaskById(taskId: Long): Task?
    fun putTask(taskId: Long, newTask: TaskDTO): Task?
    fun deleteTask(taskId: Long)
}
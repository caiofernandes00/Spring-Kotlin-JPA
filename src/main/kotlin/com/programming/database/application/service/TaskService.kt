package com.programming.database.application.service

import com.programming.database.adapter.`in`.controller.dto.TaskDTO
import com.programming.database.application.port.`in`.TaskUseCase
import com.programming.database.application.port.out.TaskRepositoryAdapter
import com.programming.database.domain.Task
import org.springframework.stereotype.Service
import java.security.InvalidParameterException
import kotlin.reflect.KClass

@Service
class TaskService(
    private val taskRepositoryAdapter: TaskRepositoryAdapter
): TaskUseCase {
    override fun getTasks(): List<Task> = taskRepositoryAdapter.getTasks()

    override fun addTask(task: TaskDTO): Task = taskRepositoryAdapter.addTask(task)

    override fun getTaskById(taskId: String): Task? {
        val taskId = convertStringParamToNumber(taskId, Long::class)
        return taskRepositoryAdapter.getTaskById(taskId)
    }

    override fun putTask(taskId: String, newTask: TaskDTO): Task? {
        val taskId = convertStringParamToNumber(taskId, Long::class)
        return taskRepositoryAdapter.putTask(taskId, newTask)
    }

    override fun deleteTask(taskId: String) {
        val taskId = convertStringParamToNumber(taskId, Long::class)
        return taskRepositoryAdapter.deleteTask(taskId)
    }

    private fun <T: Any> convertStringParamToNumber(param: String, clazz: KClass<T>): T {
        try {
            return when (clazz) {
                Long::class -> param.toLong() as T
                else -> param.toInt() as T
            }
        } catch (e: NumberFormatException) {
            println("Error parsing parameter $param")
            throw InvalidParameterException()
        }
    }
}
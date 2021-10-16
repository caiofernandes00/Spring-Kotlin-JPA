package com.programming.database.application.service

import com.programming.database.adapter.`in`.controller.dto.TaskDTO
import com.programming.database.adapter.`in`.controller.dto.TaskFiltersDTO
import com.programming.database.application.port.`in`.TaskUseCase
import com.programming.database.application.port.out.TaskRepositoryPort
import com.programming.database.domain.Task
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.security.InvalidParameterException
import kotlin.reflect.KClass

@Service
class TaskService(
    private val taskRepositoryAdapter: TaskRepositoryPort,
) : TaskUseCase {
    override fun getTasks(
        pageable: Pageable,
        fieldsRequest: TaskFiltersDTO,
    ): Page<Task> = taskRepositoryAdapter.getTasks(
        pageable, fieldsRequest
    )

    override fun addTask(task: TaskDTO): Task = taskRepositoryAdapter.addTask(task)

    override fun getTaskById(taskId: String): Task? {
        return taskRepositoryAdapter.getTaskById(
            convertStringParamToNumber(taskId, Long::class)
        )
    }

    override fun putTask(taskId: String, newTask: TaskDTO): Task? {
        return taskRepositoryAdapter.putTask(
            convertStringParamToNumber(taskId, Long::class),
            newTask
        )
    }

    override fun deleteTask(taskId: String) {
        return taskRepositoryAdapter.deleteTask(
            convertStringParamToNumber(taskId, Long::class)
        )
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T : Any> convertStringParamToNumber(param: String, clazz: KClass<T>): T {
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
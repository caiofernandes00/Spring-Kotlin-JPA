package com.programming.database.application.service

import com.programming.database.adapter.`in`.controller.dto.TaskDTO
import com.programming.database.adapter.`in`.controller.dto.TaskFiltersDTO
import com.programming.database.application.port.`in`.TaskUseCase
import com.programming.database.application.port.out.TaskPort
import com.programming.database.domain.Task
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.security.InvalidParameterException
import kotlin.reflect.KClass

@Service
class TaskService(
    private val taskPort: TaskPort,
) : TaskUseCase {
    override fun getTasks(
        pageable: Pageable,
        fieldsRequest: TaskFiltersDTO,
    ): Page<Task> = taskPort.getTasks(
        pageable, fieldsRequest
    )

    override fun addTask(task: TaskDTO): Task = taskPort.addTask(task)

    override fun getTaskById(taskId: String): Task? {
        return taskPort.getTaskById(
            convertStringParamToNumber(taskId, Long::class)
        )
    }

    override fun putTask(taskId: String, newTask: TaskDTO): Task? {
        return taskPort.putTask(
            convertStringParamToNumber(taskId, Long::class),
            newTask
        )
    }

    override fun deleteTask(taskId: String) {
        return taskPort.deleteTask(
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
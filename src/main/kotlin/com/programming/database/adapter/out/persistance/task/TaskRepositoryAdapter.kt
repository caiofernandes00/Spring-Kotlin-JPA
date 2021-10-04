package com.programming.database.adapter.out.persistance.task

import com.programming.database.adapter.`in`.controller.dto.TaskDTO
import com.programming.database.adapter.out.persistance.mapper.toDomain
import com.programming.database.adapter.out.persistance.mapper.toEntity
import com.programming.database.adapter.out.persistance.repository.TaskRepository
import com.programming.database.application.port.out.TaskRepositoryAdapter
import com.programming.database.domain.Task
import com.programming.database.adapter.out.persistance.entity.TaskEntity
import org.springframework.stereotype.Service
import java.util.NoSuchElementException

@Service
class TaskAdapter(
    private val taskRepository: TaskRepository
) : TaskRepositoryAdapter {
    override fun getTasks(): List<Task> =
        taskRepository.findAll().map { task -> task.toDomain() }

    override fun addTask(task: TaskDTO): Task =
        taskRepository.save(
            task.toEntity()
        ).toDomain()

    override fun getTaskById(taskId: Long): Task? =
        taskRepository.findById(taskId).map { task ->
            task.toDomain()
        }.orElse(null)

    @Throws(NoSuchElementException::class)
    override fun putTask(taskId: Long, newTask: TaskDTO): Task? =
        taskRepository.findById(taskId).map { currentTask ->
            val updatedTask: TaskEntity = currentTask.copy(
                title = newTask.title,
                description = newTask.description,
                status = newTask.status,
                priority = newTask.priority,
                dueDate = newTask.dueDate,
                startDate = newTask.startDate
            )

            taskRepository.save(updatedTask).toDomain()
        }.orElseThrow()

    @Throws(NoSuchElementException::class)
    override fun deleteTask(taskId: Long) {
        taskRepository.findById(taskId).map {
            taskRepository.delete(it)
        }.orElseThrow()
    }

}



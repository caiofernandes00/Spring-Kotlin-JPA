package com.programming.database.adapter.out.persistance.task

import com.programming.database.adapter.`in`.controller.dto.TaskDTO
import com.programming.database.adapter.out.persistance.entity.TaskEntity
import com.programming.database.adapter.out.persistance.mapper.toDomain
import com.programming.database.adapter.out.persistance.mapper.toEntity
import com.programming.database.adapter.out.persistance.repository.TaskRepository
import com.programming.database.application.port.out.TaskRepositoryAdapter
import com.programming.database.domain.Task
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service
import java.util.*
import javax.persistence.criteria.Predicate

@Service
class TaskAdapter(
    private val taskRepository: TaskRepository
) : TaskRepositoryAdapter {
    override fun getTasks(pageable: Pageable, startDate: Date?, endDate: Date?): Page<Task> =
        taskRepository.findAll(
            Specs.applyFilter(
                startDate, endDate
            ),
            pageable
        ).map { task -> task.toDomain() }

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

object Specs {
    fun applyFilter(
        startDate: Date?,
        endDate: Date?
    ): Specification<TaskEntity> =
        Specification<TaskEntity> { root, _, builder ->

            val predicates = mutableListOf<Predicate>()
            startDate?.let {
                predicates.add(
                    builder.greaterThanOrEqualTo(
                        root.get("startDate"),
                        it
                    )
                )
            }

            endDate?.let {
                predicates.add(
                    builder.lessThanOrEqualTo(
                        root.get("startDate"),
                        it
                    )
                )
            }

            builder.and(*predicates.toTypedArray())
        }
}


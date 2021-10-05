package com.programming.database.adapter.`in`.controller

import com.programming.database.adapter.`in`.controller.dto.TaskDTO
import com.programming.database.adapter.`in`.controller.dto.TaskFiltersDTO
import com.programming.database.adapter.`in`.controller.helper.PageableFilter
import com.programming.database.application.port.`in`.TaskUseCase
import com.programming.database.domain.Task
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/v1/api/tasks")
class TaskController(
    private val taskUseCase: TaskUseCase
) {

    @GetMapping
    fun getTasks(
        filters: TaskFiltersDTO,
        @PageableDefault(size = 10) pageable: Pageable
    ): ResponseEntity<Page<Task>> {

        val pageable = pageableFilter(pageable)
        return ResponseEntity.ok(taskUseCase.getTasks(
            pageable,
            filters.startDate,
            filters.endDate
        ))
    }

    @PostMapping
    fun addTask(@RequestBody @Valid task: TaskDTO): ResponseEntity<Task> =
        ResponseEntity.ok(taskUseCase.addTask(task))

    @GetMapping("/{id}")
    fun getTasksById(@PathVariable(value = "id") taskId: String): ResponseEntity<Task?> {
        val task = taskUseCase.getTaskById(taskId)

        return if (task != null) ResponseEntity.ok(task)
        else ResponseEntity.notFound().build()
    }

    @PutMapping("/{id}")
    fun putTask(@PathVariable(value = "id") taskId: String, @RequestBody newTask: TaskDTO): ResponseEntity<Task> {
        val task = taskUseCase.putTask(taskId, newTask)

        return if (task != null) ResponseEntity.ok(task)
        else ResponseEntity.notFound().build()
    }

    @DeleteMapping("/{id}")
    fun deleteTask(@PathVariable(value = "id") taskId: String, @RequestBody newTask: TaskDTO): ResponseEntity<Unit> =
        ResponseEntity.ok(taskUseCase.deleteTask(taskId))

    private fun pageableFilter(pageable: Pageable): Pageable {
        val mappedFields = mapOf(
            "start_date" to "startDate",
            "due_date" to "dueDate",
            "created_at" to "createdAt",
            "updated_at" to "updatedAt",
            "status" to "status"
        )

        return PageableFilter.translate(pageable, mappedFields)
    }

}
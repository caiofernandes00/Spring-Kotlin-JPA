package com.programming.database.adapter.`in`.controller

import com.programming.database.adapter.`in`.controller.dto.TaskDTO
import com.programming.database.application.port.`in`.TaskUseCase
import com.programming.database.domain.Task
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
    fun getTasks(): ResponseEntity<List<Task>> =
        ResponseEntity.ok(taskUseCase.getTasks())

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

}
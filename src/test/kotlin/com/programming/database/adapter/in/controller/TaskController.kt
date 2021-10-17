package com.programming.database.adapter.`in`.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.programming.database.adapter.out.persistance.entity.TaskEntity
import com.programming.database.adapter.out.persistance.repository.TaskRepository
import com.programming.database.domain.Task
import org.json.JSONArray
import org.json.JSONObject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.time.OffsetDateTime
import java.util.stream.Stream

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension::class)
internal class TaskController {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var repository: TaskRepository

    @Autowired
    private val objectMapper = ObjectMapper()

    private val jacksonObjectMapper = jacksonObjectMapper()

    companion object {
        @JvmStatic
        fun generateArgumentsTestsForGetTasks(): Stream<Arguments> =
            Stream.of(
                Arguments.arguments("startPeriod", OffsetDateTime.now().minusDays(1).toString()),
                Arguments.arguments("endPeriod", OffsetDateTime.now().plusDays(1).toString())
            )
    }

    @BeforeEach
    fun setup() {
        repository.deleteAll()
        repository.save(generateDatabaseRegistry())
    }

    @ParameterizedTest
    @MethodSource("generateArgumentsTestsForGetTasks")
    fun `Validate if can retrieve a task based on field params`(field: String, value: String) {
        val result = mockMvc.perform(
            MockMvcRequestBuilders.get("/v1/api/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andDo { response -> response.response }
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))

        val payloadMap = JSONObject(result.andReturn().response.contentAsString).toCustomMap() as Map<*, *>
        val pageableDataJson = objectMapper.writeValueAsString(payloadMap["pageable"])
        val pageableDataObject = jacksonObjectMapper.readValue<CustomPageable>(pageableDataJson)
        val contentJson = objectMapper.writeValueAsString(payloadMap["content"])
        val contentObject = jacksonObjectMapper.readValue<Task>(contentJson)

        // PARAMS ASSERTION
        assertEquals(true, pageableDataObject.sort.sorted)
        assertEquals(10, pageableDataObject.pageSize)

        // VALUES ASSERTION
        assertEquals(generateDatabaseRegistry().title, contentObject.title)
    }

    @Suppress("UNCHECKED_CAST")
    private fun JSONObject.toCustomMap(): Any = keys().asSequence().associateWith {
        when (val value: Map<String, *> = this[it as String?] as Map<String, *>) {
            is JSONObject -> value.toMap()
            is JSONArray -> {
                val map = (0 until value.length()).associate { Pair(it.toString(), value[it]) }
                (JSONObject(map) as Map<*, *>).toMap().values.toList()
            }
            JSONObject.NULL -> null
            else -> value
        }
    }

    private fun generateDatabaseRegistry() = TaskEntity(
        title = "Task title",
        description = "Description",
        status = 1,
        startDate = OffsetDateTime.now(),
        dueDate = OffsetDateTime.now(),
        priority = 1
    )

}

data class CustomPageable(
    val sort: CustomSort,
    val pageNumber: Int,
    val pageSize: Int,
    val offset: Int,
    val unpaged: Boolean,
    val paged: Boolean
) {
    data class CustomSort(
        val sorted: Boolean,
        val unsorted: Boolean,
        val empty: Boolean
    )
}
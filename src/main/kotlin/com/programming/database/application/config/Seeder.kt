package com.programming.database.application.config

import com.programming.database.adapter.out.persistance.entity.TaskEntity
import com.programming.database.adapter.out.persistance.repository.TaskRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Component
import java.time.OffsetDateTime

@Component
@ConditionalOnProperty(name = ["environment"], havingValue = "local", matchIfMissing = false)
class Seeder : ApplicationRunner {

    private val logger = LoggerFactory.getLogger(Seeder::class.java)

    @Autowired
    lateinit var taskRepository: TaskRepository

    override fun run(args: ApplicationArguments?) {
        logger.info("Seeding database....")

        taskRepository.save(
            TaskEntity(
                title = "Task title",
                description = "Description",
                status = 1,
                startDate = OffsetDateTime.now(),
                dueDate = OffsetDateTime.now(),
                priority = 1
            ))

        logger.info("Seeding finished....")
    }

}
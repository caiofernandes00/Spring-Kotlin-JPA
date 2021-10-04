package com.programming.database.adapter.out.persistance.repository

import com.programming.database.adapter.out.persistance.entity.TaskEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import javax.transaction.Transactional

@Repository
@Transactional(Transactional.TxType.MANDATORY)
interface TaskRepository : JpaRepository<TaskEntity, Long>
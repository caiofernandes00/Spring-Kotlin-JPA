package com.programming.database.adapter.out.persistance.repository

import com.programming.database.adapter.out.persistance.entity.TaskEntity
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository
import javax.transaction.Transactional

@Repository
@Transactional(Transactional.TxType.MANDATORY)
interface TaskRepository : PagingAndSortingRepository<TaskEntity, Long>, JpaSpecificationExecutor<TaskEntity>
package com.programming.database.adapter.`in`.controller.helper

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort

object PageableFilter {
    fun translate(pageable: Pageable, fieldsToMap: Map<String, String>): Pageable {
        val orders = pageable.sort
            .filter { field -> fieldsToMap.containsKey(field.property) }
            .map { field ->
                fieldsToMap[field.property]?.let {
                    Sort.Order(
                        field.direction,
                        it
                    )
                }
            }

        return PageRequest.of(
            pageable.pageNumber, pageable.pageSize,
            Sort.by(orders.toList())
        )
    }
}
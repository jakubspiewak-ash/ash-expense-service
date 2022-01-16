package com.jakubspiewak.ash.expense

import com.jakubspiewak.ash.expense.model.ExpenseCreateRequest
import com.jakubspiewak.ash.expense.model.ExpenseGetResponse
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Criteria.where
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.YearMonth
import java.util.*

@Service
class ExpenseServiceImpl(
    private val repository: ExpenseRepository,
    private val mapper: ExpenseMapper,
    private val validator: ExpenseValidator
) : ExpenseService {
    override fun create(request: ExpenseCreateRequest) {
        validator.create(request)
        repository.save(mapper.mapToEntity(UUID.randomUUID(), request))
    }

    override fun read(month: YearMonth?): List<ExpenseGetResponse> {
        val query = Query()
        month?.let {
            val startOfMonth: LocalDate = it.atDay(1)
            val endOfMonth: LocalDate = it.atEndOfMonth()
            val criteria = Criteria().orOperator(
                where("date.start").isNull.and("date.end").isNull,
                where("date.start").isNull.and("date.end").gte(startOfMonth),
                where("date.start").lte(endOfMonth).and("date.end").isNull,
                where("date.start").lte(endOfMonth).and("date.end").gte(startOfMonth),
            )
            query.addCriteria(criteria)
        }
        return repository.find(query).map(mapper::mapToResponse)
    }

    override fun update(id: UUID, request: ExpenseCreateRequest) {
        validator.update(id, request)
        repository.save(mapper.mapToEntity(id, request))
    }

    override fun delete(id: UUID) {
        validator.delete(id)
        repository.deleteById(id)
    }
}
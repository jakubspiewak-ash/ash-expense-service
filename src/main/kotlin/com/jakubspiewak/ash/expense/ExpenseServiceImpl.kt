package com.jakubspiewak.ash.expense

import com.jakubspiewak.ash.expense.model.ExpenseCreateRequest
import com.jakubspiewak.ash.expense.model.ExpenseGetResponse
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service
import java.time.YearMonth
import java.util.*

@Primary
@Service
class ExpenseServiceImpl(
    private val repository: ExpenseRepository,
    private val mapper: ExpenseMapper,
    private val validator: ExpenseValidator
) : ExpenseService {
    override fun create(request: ExpenseCreateRequest)  {
        validator.create(request)
        repository.save(mapper.mapToEntity(UUID.randomUUID(), request))
    }

    override fun read(month: YearMonth?): List<ExpenseGetResponse> {
        return repository.readAll().map { mapper.mapToResponse(it) }
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
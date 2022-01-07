package com.jakubspiewak.ash.expense

import com.jakubspiewak.ash.expense.model.ExpenseCreateRequest
import com.jakubspiewak.ash.expense.model.ExpenseGetResponse
import org.springframework.stereotype.Service
import java.util.*

@Service
class ExpenseMapper {
    fun mapToEntity(id: UUID?, request: ExpenseCreateRequest) = ExpenseDocument(
        id = id,
        name = request.name,
        amount = request.amount,
        date = request.date,
        mailConfig = request.mailConfig,
    )

    fun mapToResponse(document: ExpenseDocument) = ExpenseGetResponse(
        id = document.id ?: throw RuntimeException(),
        name = document.name,
        amount = document.amount,
        date = document.date,
        mailConfig = document.mailConfig,
    )
}
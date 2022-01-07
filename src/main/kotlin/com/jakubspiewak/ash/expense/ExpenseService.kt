package com.jakubspiewak.ash.expense

import com.jakubspiewak.ash.expense.model.ExpenseCreateRequest
import com.jakubspiewak.ash.expense.model.ExpenseGetResponse
import java.time.YearMonth
import java.util.*

interface ExpenseService {
    fun create(request: ExpenseCreateRequest)
    fun read(month: YearMonth?): List<ExpenseGetResponse>
    fun update(id: UUID, request: ExpenseCreateRequest)
    fun delete(id: UUID)
}
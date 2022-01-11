package com.jakubspiewak.ash.expense

import com.jakubspiewak.ash.expense.model.DateRange
import com.jakubspiewak.ash.expense.model.ExpenseAmount
import com.jakubspiewak.ash.expense.model.MailConfig
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document("expense")
data class ExpenseDocument(
    @Id
    val id: UUID?,
    val name: String,
    val amount: ExpenseAmount,
    val mailConfig: MailConfig?,
    val date: DateRange
)
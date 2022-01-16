package com.jakubspiewak.ash.expense.model

import java.util.*

data class ExpenseGetResponse(
    val id: UUID?,
    val name: String,
    val amount: ExpenseAmount,
    val date: DateRange,
    val mailConfig: MailConfig?
)

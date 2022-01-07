package com.jakubspiewak.ash.expense.model

data class ExpenseCreateRequest(
    val name: String,
    val amount: ExpenseAmount,
    val date: DateRange,
    val mailConfig: MailConfig?
)

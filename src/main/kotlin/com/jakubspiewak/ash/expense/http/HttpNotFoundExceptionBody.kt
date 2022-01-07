package com.jakubspiewak.ash.expense.http

import java.util.*

data class HttpNotFoundExceptionBody(
    val message: String,
    val timestamp : Date,
)

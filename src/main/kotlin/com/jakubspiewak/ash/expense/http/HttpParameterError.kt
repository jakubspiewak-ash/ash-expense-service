package com.jakubspiewak.ash.expense.http

data class HttpParameterError(
    val parameter: String,
    val message: String
)

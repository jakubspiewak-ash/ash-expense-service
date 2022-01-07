package com.jakubspiewak.ash.expense.http

import java.util.*

data class HttpValidationExceptionBody(
    val errors: List<HttpParameterError>,
    val timestamp : Date,
)

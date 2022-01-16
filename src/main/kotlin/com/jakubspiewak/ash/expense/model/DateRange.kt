package com.jakubspiewak.ash.expense.model

import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate

data class DateRange(
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    val start: LocalDate?,
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    val end: LocalDate?
)

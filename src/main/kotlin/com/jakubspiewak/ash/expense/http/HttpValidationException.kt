package com.jakubspiewak.ash.expense.http

import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(BAD_REQUEST)
class HttpValidationException(val body: HttpValidationExceptionBody) : RuntimeException()
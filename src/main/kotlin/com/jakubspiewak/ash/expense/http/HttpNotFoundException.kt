package com.jakubspiewak.ash.expense.http

class HttpNotFoundException(val body: HttpNotFoundExceptionBody) : RuntimeException()
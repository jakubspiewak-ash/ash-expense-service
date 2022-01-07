package com.jakubspiewak.ash.expense.http

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class HttpExceptionHandler {
    @ExceptionHandler(HttpValidationException::class)
    fun handleValidationException(exception: HttpValidationException): ResponseEntity<HttpValidationExceptionBody> {
        return ResponseEntity(exception.body, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(HttpNotFoundException::class)
    fun handleNotFound(exception: HttpNotFoundException): ResponseEntity<HttpNotFoundExceptionBody> {
        return ResponseEntity(exception.body, HttpStatus.NOT_FOUND)
    }
}
package com.jakubspiewak.ash.expense

import com.jakubspiewak.ash.expense.model.ExpenseCreateRequest
import com.jakubspiewak.ash.expense.model.ExpenseGetResponse
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.OK
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.YearMonth
import java.util.*

@RestController
@RequestMapping("/expense")
class ExpenseController(private val service: ExpenseService) {

    @PostMapping
    fun create(@RequestBody request: ExpenseCreateRequest): ResponseEntity<Unit> {
        service.create(request)
        return ResponseEntity.status(CREATED).build()
    }

    @GetMapping
    fun read(@RequestParam("month") month: YearMonth?): ResponseEntity<List<ExpenseGetResponse>> {
        val body = service.read(month)
        return ResponseEntity.status(OK).body(body)
    }

    @PutMapping("/{id}")
    fun update(@RequestBody request: ExpenseCreateRequest, @PathVariable("id") id: UUID): ResponseEntity<Unit> {
        service.update(id, request)
        return ResponseEntity.status(OK).build()
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable("id") id: UUID): ResponseEntity<Unit> {
        service.delete(id)
        return ResponseEntity.status(OK).build()
    }
}
package com.jakubspiewak.ash.expense

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories
import org.springframework.scheduling.annotation.EnableScheduling

@EnableMongoRepositories
@SpringBootApplication
open class AshExpenseServiceApplication

fun main(args: Array<String>) {
    runApplication<AshExpenseServiceApplication>(*args)
}

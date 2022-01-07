package com.jakubspiewak.ash.expense

import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*

interface ExpenseRepository : MongoRepository<ExpenseDocument, UUID>
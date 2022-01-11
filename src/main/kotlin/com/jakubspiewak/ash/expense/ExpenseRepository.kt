package com.jakubspiewak.ash.expense

import com.jakubspiewak.ash.expense.mongo.MultiTenantMongoRepository
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Repository

@Repository
class ExpenseRepository(mongoTemplate: MongoTemplate) :
    MultiTenantMongoRepository<ExpenseDocument>(ExpenseDocument::class.java, mongoTemplate)
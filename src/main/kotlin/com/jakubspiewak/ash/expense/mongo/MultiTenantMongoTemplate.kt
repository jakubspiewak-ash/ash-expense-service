package com.jakubspiewak.ash.expense.mongo

import org.springframework.data.mongodb.MongoDatabaseFactory
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Component

@Component
open class MultiTenantMongoTemplate(mongoDbFactory: MongoDatabaseFactory) : MongoTemplate(mongoDbFactory) {
    override fun getCollectionName(entityClass: Class<*>): String {
        val collectionName = super.getCollectionName(entityClass)
        val userId = DatabaseContextHolder.getCurrentUserId()
        return userId?.let { "$collectionName-$userId" } ?: collectionName
    }
}

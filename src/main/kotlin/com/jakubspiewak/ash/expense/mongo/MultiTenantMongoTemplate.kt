package com.jakubspiewak.ash.expense.mongo

import org.springframework.data.mongodb.MongoDatabaseFactory
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.convert.MappingMongoConverter

open class MultiTenantMongoTemplate(
    mongoDbFactory: MongoDatabaseFactory,
    mongoConverter: MappingMongoConverter
) : MongoTemplate(mongoDbFactory, mongoConverter) {

    override fun getCollectionName(entityClass: Class<*>): String {
        val collectionName = super.getCollectionName(entityClass)
        val userId = DatabaseContextHolder.getCurrentUserId()
        return userId?.let { "$collectionName-$userId" } ?: collectionName
    }
}

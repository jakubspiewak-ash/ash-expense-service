package com.jakubspiewak.ash.expense.mongo

import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import java.util.*

open class MultiTenantMongoRepository<T>(private val clazz: Class<T>, private val mongoTemplate: MongoTemplate) {
    fun save(document: T) {
        mongoTemplate.save(document)
    }

    fun deleteById(id: UUID) {
        mongoTemplate.remove(Query.query(Criteria.where("id").`is`(id)), clazz)
    }

    fun existsById(id: UUID): Boolean = mongoTemplate.exists(Query.query(Criteria.where("id").`is`(id)), clazz)

    fun readAll(): List<T> = mongoTemplate.findAll(clazz)

    fun readById(id: UUID): T? = mongoTemplate.findById(id, clazz)

    fun find(query: Query): List<T> = mongoTemplate.find(query, clazz)
}
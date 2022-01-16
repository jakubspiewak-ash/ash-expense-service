package com.jakubspiewak.ash.expense.mongo

import com.mongodb.MongoClientSettings
import com.mongodb.MongoCredential
import com.mongodb.ServerAddress
import org.bson.UuidRepresentation.STANDARD
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.mongodb.MongoDatabaseFactory
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.convert.MappingMongoConverter
import org.springframework.data.mongodb.core.convert.MongoCustomConversions

@Configuration
open class MultiTenantMongoConfiguration : AbstractMongoClientConfiguration() {

    override fun getDatabaseName(): String {
        return "admin"
    }

    @Bean
    @Primary
    override fun mongoTemplate(
        databaseFactory: MongoDatabaseFactory,
        mappingMongoConverter: MappingMongoConverter
    ): MongoTemplate = MultiTenantMongoTemplate(databaseFactory, mappingMongoConverter)

    override fun configureClientSettings(builder: MongoClientSettings.Builder) {
        builder
            .credential(MongoCredential.createCredential("root", "admin", "root".toCharArray()))
            .codecRegistry(MongoClientSettings.getDefaultCodecRegistry())
            .uuidRepresentation(STANDARD)
            .applyToClusterSettings { it.hosts(listOf(ServerAddress("127.0.0.1", 27017))) }
    }

    override fun configureConverters(converter: MongoCustomConversions.MongoConverterConfigurationAdapter) {
        converter.useNativeDriverJavaTimeCodecs()
    }
}

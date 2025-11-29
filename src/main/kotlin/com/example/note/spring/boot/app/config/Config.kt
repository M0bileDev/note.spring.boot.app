package com.example.note.spring.boot.app.config

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MongoConfig {

    @Bean
    fun mongoClient(): MongoClient {
        val mongoDbUri = System.getenv("MONGODB_URI")
        val connectionString = ConnectionString(mongoDbUri)

        val settings = MongoClientSettings.builder()
            .applyConnectionString(connectionString)
            .build()

        return MongoClients.create(settings)
    }
}
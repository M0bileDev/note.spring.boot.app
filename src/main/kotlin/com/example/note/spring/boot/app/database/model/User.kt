package com.example.note.spring.boot.app.database.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("users")
data class User(
    val email: String,
    val hashedPassword: String,
    //mongodb primary auto-increment id
    @Id val id: ObjectId = ObjectId()
)

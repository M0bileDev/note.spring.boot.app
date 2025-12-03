package com.example.note.spring.boot.app.database.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id

data class User(
    val email: String,
    val password: String,
    //mongodb primary auto-increment id
    @Id val id: ObjectId = ObjectId()
)

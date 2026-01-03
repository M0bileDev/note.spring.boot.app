package com.example.note.spring.boot.app.database.model

import com.example.note.spring.boot.app.security.Token
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

//mongodb annotation this model is a document type -> refresh token collection
@Document("refresh_tokens")
data class RefreshToken(
    val userId: ObjectId,
    val hashedToken: Token,
//    mongo atlas db will periodically check expiresAt property and remove it from db when conditions are met
//    this will not be created automatically, need to update properties config -> spring.data.mongodb.auto-index-creation=true
    @Indexed(expireAfter = "0s")
    val expiresAt: Instant,
    val createdAt: Instant = Instant.now()
)
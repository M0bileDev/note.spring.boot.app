package com.example.note.spring.boot.app.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

//mongodb annotation this model is a document type -> notes collection
@Document("notes")
data class Note(
    val title: String,
    val content: String,
    val color: Long,
    val createdAt: Instant,
    //mongodb auto-increment id
    @Id val id: ObjectId = ObjectId.get()
)
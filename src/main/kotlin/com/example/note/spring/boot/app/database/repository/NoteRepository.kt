package com.example.note.spring.boot.app.database.repository

import com.example.note.spring.boot.app.database.model.Note
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface NoteRepository : MongoRepository<Note, ObjectId> {
    // query will be generated under the hood, name convention
    // for this function must be strict for mongodb
    fun findByOwnerId(ownerId: ObjectId): List<Note>
}
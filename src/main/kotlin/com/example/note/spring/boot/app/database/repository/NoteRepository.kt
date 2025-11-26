package com.example.note.spring.boot.app.database.repository

import com.example.note.spring.boot.app.database.model.Note
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface NoteRepository : MongoRepository<Note, ObjectId> {
}
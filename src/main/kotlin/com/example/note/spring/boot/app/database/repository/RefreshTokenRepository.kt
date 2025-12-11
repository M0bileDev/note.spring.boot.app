package com.example.note.spring.boot.app.database.repository

import com.example.note.spring.boot.app.database.model.RefreshToken
import com.example.note.spring.boot.app.security.Token
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface RefreshTokenRepository : MongoRepository<RefreshToken, ObjectId> {
    fun findByUserIdAndHashedToken(userId: ObjectId, hashedToken: Token): RefreshToken?
    fun deleteByUserIdAndHashedToken(userId: ObjectId, hashedToken: Token)
}
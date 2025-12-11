package com.example.note.spring.boot.app.security

import com.example.note.spring.boot.app.database.model.User
import com.example.note.spring.boot.app.database.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val jwtService: JwtService,
    private val userRepository: UserRepository,
    private val hashEncoder: HashEncoder
) {
    fun register(email: String, password: Password): User {
        return userRepository.save(
            User(
                email =  email,
                hashedPassword = hashEncoder.encode(password)
            )
        )
    }
}
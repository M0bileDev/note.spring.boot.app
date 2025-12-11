package com.example.note.spring.boot.app.security

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component

typealias Password = String

@Component
class HashEncoder {

    private val bCrypt = BCryptPasswordEncoder()

    fun encode(raw: String): Password {
        return bCrypt.encode(raw) ?: throw IllegalStateException("Password cannot be empty")
    }

    fun matches(raw: String, password: Password): Boolean = bCrypt.matches(raw, password)
}
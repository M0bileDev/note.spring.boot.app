package com.example.note.spring.boot.app.security

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component

typealias Password = String?

@Component
class HashEncoder {

    private val bCrypt = BCryptPasswordEncoder()

    fun encode(raw: String): Password = bCrypt.encode(raw)

    fun matches(raw: String, password: Password): Boolean = bCrypt.matches(raw, password)
}
package com.example.note.spring.boot.app.security

import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import java.util.*

typealias JwtSecret = String

class JwtService(
//    Inject application property at runtime
    @Value("JWT_SECRET") private val jwtSecret: JwtSecret
) {

    val secretKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(jwtSecret))

}
package com.example.note.spring.boot.app.security

import com.example.note.spring.boot.app.controllers.OwnerId
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.*

typealias JwtSecret = String
typealias Token = String

@Service
class JwtService(
//    Inject application property at runtime
    @Value("JWT_SECRET") private val jwtSecret: JwtSecret
) {

    enum class TokenType {
        ACCESS,
        REFRESH
    }

    private val secretKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(jwtSecret))

    //                                      15 minutes      60 seconds  1 second
    private val accessTokenValidityMs = 15 * 60 * 1000L

    //                               30 days    24 hours    60 minutes  60 seconds  1 second
    val refreshTokenValidityMs = 30 * 24 * 60 * 60 * 1000L

    private fun generateJwtToken(
        userId: OwnerId,
        type: String,
        expiry: Long
    ): Token {
        val now = Date()
        val expiryDate = Date(now.time + expiry)
        return Jwts.builder()
//           owner of the token
            .subject(userId)
//           additional info
            .claim("type", type)
            .issuedAt(now)
            .expiration(expiryDate)
            .signWith(secretKey, Jwts.SIG.HS256)
//            take all above information and create JWT
            .compact()
    }

    fun generateAccessToken(userId: OwnerId): Token {
        return generateJwtToken(userId, TokenType.ACCESS.name, accessTokenValidityMs)
    }

    fun generateRefreshToken(userId: OwnerId): Token {
        return generateJwtToken(userId, TokenType.REFRESH.name, refreshTokenValidityMs)
    }
}
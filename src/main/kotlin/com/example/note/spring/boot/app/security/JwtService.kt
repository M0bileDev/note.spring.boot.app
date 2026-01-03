package com.example.note.spring.boot.app.security

import com.example.note.spring.boot.app.controllers.OwnerId
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.util.*

typealias JwtSecret = String
typealias Token = String

const val TYPE = "type"
const val STARTS_WITH_BEARER = "Bearer "

@Service
class JwtService(
//    Inject application property at runtime
    @Value("\${JWT_SECRET}") private val jwtSecret: JwtSecret
) {

    enum class TokenType {
        ACCESS,
        REFRESH
    }

    private val secretKey = Keys.hmacShaKeyFor(jwtSecret.toByteArray())

    //                                  15 minutes      60 seconds  1 second
    private val accessTokenValidityMs = 15              * 60        * 1000L

    //                           30 days    24 hours    60 minutes  60 seconds  1 second
    val refreshTokenValidityMs = 30         * 24        * 60        * 60        * 1000L

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
            .claim(TYPE, type)
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

    fun validateAccessToken(token: Token): Boolean {
        val claims = parseAllClaims(token) ?: return false
        val tokenType = claims[TYPE] as? String ?: return false
        return tokenType == TokenType.ACCESS.name
    }

    fun validateRefreshToken(token: Token): Boolean {
        val claims = parseAllClaims(token) ?: return false
        val tokenType = claims[TYPE] as? String ?: return false
        return tokenType == TokenType.REFRESH.name
    }

    fun getUserIdFromToken(token: Token): OwnerId {
        val claims = parseAllClaims(token) ?: throw ResponseStatusException(HttpStatusCode.valueOf(401), "Refresh token not recognized.")
        return claims.subject
    }

    private fun parseAllClaims(token: Token): Claims? {
        val rawToken = if (token.startsWith(STARTS_WITH_BEARER)) {
            token.removePrefix(STARTS_WITH_BEARER)
        } else token
        return try {
            Jwts.parser()
//                Check if user changed some parts of the token
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(rawToken)
                .payload
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
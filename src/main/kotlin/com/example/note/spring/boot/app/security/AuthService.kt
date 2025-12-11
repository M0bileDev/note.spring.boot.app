package com.example.note.spring.boot.app.security

import com.example.note.spring.boot.app.database.model.User
import com.example.note.spring.boot.app.database.repository.UserRepository
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.stereotype.Service
import javax.security.auth.login.CredentialException

typealias Email = String

data class TokenPair(
    val accessToken: Token,
    val refreshToken: Token
)

@Service
class AuthService(
    private val jwtService: JwtService,
    private val userRepository: UserRepository,
    private val hashEncoder: HashEncoder
) {
    fun register(email: Email, password: Password): User {
        return userRepository.save(
            User(
                email = email,
                hashedPassword = hashEncoder.encode(password)
            )
        )
    }

    fun login(email: Email, password: Password): TokenPair {
        val user = userRepository.findByEmail(email) ?: throw CredentialException("Invalid credentials.")

        if (!hashEncoder.matches(password, user.hashedPassword)) {
            throw BadCredentialsException("Invalid credentials.")
        }

        val newAccessToken = jwtService.generateAccessToken(user.id.toHexString())
        val newRefreshToken = jwtService.generateRefreshToken(user.id.toHexString())

        return TokenPair(
            accessToken = newAccessToken,
            refreshToken = newRefreshToken
        )
    }
}
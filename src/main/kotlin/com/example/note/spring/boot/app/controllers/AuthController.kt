package com.example.note.spring.boot.app.controllers

import com.example.note.spring.boot.app.security.*
import jakarta.validation.constraints.Pattern
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService
) {

    data class AuthRequest(
        @field:jakarta.validation.constraints.Email(message = "Invalid email format.")
        val email: Email,
        //at least  one lowercase, one uppercase, one digit and must be 9 characters long
        @field:Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d). {9,}\$",
            message = "Password must contains at least one lowercase, one uppercase, one digit and must be 9 characters long."
        )
        val password: Password
    )

    data class RefreshRequest(
        val refreshToken: Token
    )

    @PostMapping("/register")
    fun register(
        @RequestBody body: AuthRequest
    ) = with(body) {
        authService.register(email, password)
    }

    @PostMapping("/login")
    fun login(
        @RequestBody body: AuthRequest
    ): TokenPair = with(body) {
        return authService.login(email, password)
    }

    @PostMapping("/refresh")
    fun refresh(
        @RequestBody body: RefreshRequest
    ): TokenPair = with(body) {
        return authService.refresh(refreshToken)
    }
}
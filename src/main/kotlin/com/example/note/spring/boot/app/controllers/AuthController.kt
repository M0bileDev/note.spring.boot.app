package com.example.note.spring.boot.app.controllers

import com.example.note.spring.boot.app.security.*
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
        val email: Email,
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
}
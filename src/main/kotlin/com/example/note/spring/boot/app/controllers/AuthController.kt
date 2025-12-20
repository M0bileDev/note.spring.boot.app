package com.example.note.spring.boot.app.controllers

import com.example.note.spring.boot.app.security.AuthService
import com.example.note.spring.boot.app.security.Email
import com.example.note.spring.boot.app.security.Password
import com.example.note.spring.boot.app.security.Token
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

}
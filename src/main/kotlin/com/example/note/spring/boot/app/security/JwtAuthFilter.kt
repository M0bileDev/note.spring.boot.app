package com.example.note.spring.boot.app.security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

private const val AUTHORIZATION = "Authorization"

@Component
class JwtAuthFilter(
    private val jwtService: JwtService
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
//      Bearer <token>
        val authHeader = request.getHeader(AUTHORIZATION)
        if (authHeader == null || !authHeader.startsWith(STARTS_WITH_BEARER)) filterChain.doFilter(request, response)

        if (!jwtService.validateAccessToken(authHeader)) filterChain.doFilter(request, response)

        val ownerId = jwtService.getUserIdFromToken(authHeader)
        val auth = UsernamePasswordAuthenticationToken(ownerId, null)
//        Change global security context of SpringBoot -> global object that allow to retrieve
//        authentication information of user anywhere in the project
        SecurityContextHolder.getContext().authentication = auth

        filterChain.doFilter(request, response)
    }

}
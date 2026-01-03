package com.example.note.spring.boot.app.security

import jakarta.servlet.DispatcherType
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.HttpStatusEntryPoint
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

// use this class to configure some parts of the app
@Configuration
class SecurityConfig(
    private val jwtAuthFilter: JwtAuthFilter
) {

    //  httpSecurity -> security related functionality
    //  SecurityFilterChain -> processed internally by SpringBoot to apply
    //  security related changes
    @Bean
    fun filterChain(httpSecurity: HttpSecurity): SecurityFilterChain {
        return httpSecurity
            .csrf { csrf -> csrf.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests { auth ->
                auth
                    //permit all endpoints under auth that do not need authorization headers
                    .requestMatchers("/auth/**")
                    .permitAll()
                    //until dispatcher is set SpringBoot maps all error to 403, but in case more
                    //fine-grained error are needed dispatcher has to be added
                    .dispatcherTypeMatchers(
                        DispatcherType.ERROR,
                        DispatcherType.FORWARD
                    )
                    .permitAll()
                    //after the rules from above any request has to be authenticated
                    .anyRequest()
                    .authenticated()
            }
            .exceptionHandling { configure ->
                //if authentication fails, by default http status code will be 401
                configure.authenticationEntryPoint(HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
            }
            .addFilterBefore(
                jwtAuthFilter, UsernamePasswordAuthenticationFilter::class.java
            )
            .build()
    }
}
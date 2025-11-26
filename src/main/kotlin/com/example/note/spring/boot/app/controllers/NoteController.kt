package com.example.note.spring.boot.app.controllers

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.Instant

//class created to control rest requests
@RestController
//map all requests to a specific relative url
@RequestMapping("/notes")
class NoteController {

    data class NoteRequest(
        val id: String?,
        val title: String,
        val content: String,
        val color: Long,
    )

    data class NoteResponse(
        val id: String,
        val title: String,
        val content: String,
        val color: Long,
        val createdAt: Instant,
    )

    fun save(body: NoteRequest) {
        TODO("implementation")
    }
}
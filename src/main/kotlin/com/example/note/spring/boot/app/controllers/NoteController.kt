package com.example.note.spring.boot.app.controllers

import com.example.note.spring.boot.app.controllers.NoteController.NoteResponse
import com.example.note.spring.boot.app.database.model.Note
import com.example.note.spring.boot.app.database.repository.NoteRepository
import org.bson.types.ObjectId
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import java.time.Instant

typealias OwnerId = String
typealias NoteId = String

//class created to control rest requests
@RestController
//map all requests to a specific relative url
@RequestMapping("/notes")
class NoteController(
    private val noteRepository: NoteRepository
) {

    //parse this model from json is handled by springboot framework
    data class NoteRequest(
        val id: NoteId?,
        val title: String,
        val content: String,
        val color: Long,
    )

    //parse this model to json is handled by springboot framework
    data class NoteResponse(
        val id: NoteId,
        val title: String,
        val content: String,
        val color: Long,
        val createdAt: Instant,
    )

    // handles POST request -> POST http://hostname/notes -> body {}
    @PostMapping
    fun save(
        @RequestBody body: NoteRequest
    ): NoteResponse {
        val ownerId = SecurityContextHolder.getContext().authentication?.principal as String
        //usually there is created mapper but for simplicity
        val note = noteRepository.save<Note>(
            Note(
                //if there is an id the user wants to update note, otherwise create new one
                id = body.id?.let { ObjectId(it) } ?: ObjectId.get(),
                title = body.title,
                content = body.content,
                color = body.color,
                createdAt = Instant.now(),
                //temp, each time new note is created for new user
                ownerId = ObjectId(ownerId)
            )
        )

        return note.toResponse()
    }

    // handles GET requests -> GET http://hostname/notes?ownerId=123
    @GetMapping
    fun findByOwnerId(): List<NoteResponse> {
//        Actual user id attached to the token
        val ownerId = SecurityContextHolder.getContext().authentication?.principal as String
        return noteRepository.findByOwnerId(ownerId = ObjectId(ownerId)).map { it.toResponse() }
    }

    // handles DELETE requests -> DELETE http://hostname/notes/123
    @DeleteMapping(path = ["/{id}"])
    fun deleteById(@PathVariable id: NoteId) {
//        Note can be shared between multiple users by only owner of the note can delete it
        val note = noteRepository.findById(ObjectId(id)).orElseThrow {
            IllegalArgumentException("Note not found")
        }
        val ownerId = SecurityContextHolder.getContext().authentication?.principal as String
        if (note.ownerId.toHexString() == ownerId) {
            noteRepository.deleteById(ObjectId(id))
        }
    }
}

private fun Note.toResponse(): NoteResponse =
    NoteResponse(
        //convert to real string from mongo db
        id = id.toHexString(),
        title = title,
        content = content,
        color = color,
        createdAt = createdAt
    )


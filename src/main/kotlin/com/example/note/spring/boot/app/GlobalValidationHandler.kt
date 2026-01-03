package com.example.note.spring.boot.app

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

//Attach fields constraint to the final response

@RestControllerAdvice
class GlobalValidationHandler {

    //function will be called for every single method not valid exception
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationError(manve: MethodArgumentNotValidException): ResponseEntity<Map<String, Any>> {
        //when validation for any field in codebase fails
        val errors = manve.bindingResult.allErrors.map {
            it.defaultMessage ?: "Invalid value."
        }
        return ResponseEntity
            //Bad request
            .status(400)
            .body(mapOf("errors" to errors))
    }
}
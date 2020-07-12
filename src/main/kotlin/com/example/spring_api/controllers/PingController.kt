package com.example.spring_api.controllers

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class PingController {

    @GetMapping("/ping")
    fun handlePing(): ResponseEntity<String> {
        return ResponseEntity.ok("pong")
    }
}

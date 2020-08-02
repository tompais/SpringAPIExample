package com.example.spring_api.controllers

import org.springframework.http.HttpStatus.OK
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class PingController {

    @GetMapping("/ping")
    @ResponseStatus(OK)
    fun handlePing(): String {
        return "pong"
    }
}

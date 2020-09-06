package com.example.spring_api.unit.controllers

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.example.spring_api.controllers.HelloController
import org.junit.jupiter.api.Test

class HelloControllerTest {
    private val helloController = HelloController()

    @Test
    fun helloTest() {
        assertThat(helloController.hello()).isEqualTo("hello")
    }
}

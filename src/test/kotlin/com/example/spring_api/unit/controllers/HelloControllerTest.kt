package com.example.spring_api.unit.controllers

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.example.spring_api.controllers.HelloController
import org.junit.jupiter.api.Test

class HelloControllerTest {
    @Test
    fun helloTest() {
        val helloController = HelloController()

        assertThat(helloController.hello()).isEqualTo("hello")
    }
}

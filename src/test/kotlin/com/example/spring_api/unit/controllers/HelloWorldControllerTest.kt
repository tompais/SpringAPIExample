package com.example.spring_api.unit.controllers

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.example.spring_api.controllers.HelloWorldController
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
class HelloWorldControllerTest {
    @Test
    fun helloWorldTest() {
        // GIVEN
        val helloWorldController = HelloWorldController()

        // WHEN
        val hello = helloWorldController.hello()

        // THEN
        assertThat(hello).isEqualTo("hello")
    }
}

package com.example.spring_api.unit.controllers

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.example.spring_api.controllers.PingController
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
class PingControllerTest {
    @Test
    fun handlePingTest() {
        // GIVEN
        val expectedResponse = "pong"
        val pingController = PingController()

        // WHEN
        val actualResponse = pingController.handlePing()

        // THEN
        assertThat(actualResponse).isEqualTo(expectedResponse)
    }
}

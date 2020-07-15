package com.example.spring_api.integration

import io.restassured.module.mockmvc.RestAssuredMockMvc
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.web.context.WebApplicationContext

@ActiveProfiles("test")
@ExtendWith(SpringExtension::class)
@SpringBootTest
@TestInstance(PER_CLASS)
@ContextConfiguration
class BaseEndpointTest {
    @Autowired
    private lateinit var webApplicationContext: WebApplicationContext

    @BeforeAll
    fun setUp() {
        RestAssuredMockMvc.webAppContextSetup(webApplicationContext)
    }
}

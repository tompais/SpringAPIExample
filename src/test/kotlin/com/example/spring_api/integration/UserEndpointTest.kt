package com.example.spring_api.integration

import com.example.spring_api.databases.repositories.IUserRepository
import com.example.spring_api.models.User
import com.example.spring_api.models.User.Status.ACTIVE
import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.Instant
import java.util.Date

@ExtendWith(SpringExtension::class)
@SpringBootTest
@AutoConfigureMockMvc
class UserEndpointTest {
    @MockkBean
    private lateinit var userRepository: IUserRepository

    @Autowired
    private lateinit var mvc: MockMvc

    @Qualifier("snakeCase")
    @Autowired
    private lateinit var mapper: ObjectMapper

    private val userId = 1L
    private val user = User(
        userId,
        "Pepe",
        "Martínez",
        "pepe.martinez@gmail.com",
        Date.from(Instant.now())
    )

    @Test
    fun getByIdTest() {
        // GIVEN
        val userAsJson = mapper.writeValueAsString(user)
        every {
            userRepository.findByIdAndStatus(userId, ACTIVE)
        } returns user

        // WHEN - THEN
        mvc.perform(get("/users/$userId"))
            .andExpect(status().isOk)
            .andExpect(content().json(userAsJson))
    }
}

package com.example.spring_api.integration

import com.example.spring_api.databases.repositories.IUserRepository
import com.example.spring_api.models.User
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.text.SimpleDateFormat

@ActiveProfiles("test")
@ExtendWith(SpringExtension::class)
@SpringBootTest
@AutoConfigureMockMvc
class UserEndpointTest {
    @Autowired
    private lateinit var userRepository: IUserRepository

    @Autowired
    private lateinit var mvc: MockMvc

    @Test
    fun getByIdTest() {
        // GIVEN
        val user = userRepository.saveAndFlush(
            User(
                0,
                "Pepe",
                "Martínez",
                "pepe.martinez@gmail.com",
                SimpleDateFormat("yyyy-MM-dd").parse("1995-11-15")
            )
        )

        // WHEN - THEN
        mvc.perform(get("/users/${user.id}"))
            .andExpect(status().isOk)
    }
}

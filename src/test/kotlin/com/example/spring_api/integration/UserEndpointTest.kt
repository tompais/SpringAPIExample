package com.example.spring_api.integration

import com.example.spring_api.databases.repositories.IUserRepository
import com.example.spring_api.models.User
import io.restassured.module.mockmvc.RestAssuredMockMvc.given
import org.hamcrest.CoreMatchers.equalTo
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus.OK
import java.text.SimpleDateFormat

class UserEndpointTest : BaseEndpointTest() {
    @Autowired
    private lateinit var userRepository: IUserRepository

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
        given()
            .`when`()
            .get("/users/${user.id}")
            .then()
            .assertThat()
            .statusCode(equalTo(OK.value()))
            .and()
            .body("email", equalTo(user.email))
    }
}

package com.example.spring_api.integration

import assertk.Assert
import assertk.assertThat
import assertk.assertions.isNotNull
import assertk.assertions.isSuccess
import assertk.assertions.support.expected
import com.example.spring_api.databases.repositories.IUserRepository
import com.example.spring_api.models.User
import com.example.spring_api.models.User.Status.ACTIVE
import com.example.spring_api.models.User.Status.INACTIVE
import com.example.spring_api.utils.MockUtils.mockUser
import com.fasterxml.jackson.databind.ObjectMapper
import io.restassured.http.ContentType.JSON
import io.restassured.module.mockmvc.RestAssuredMockMvc.`when`
import io.restassured.module.mockmvc.RestAssuredMockMvc.given
import org.hamcrest.CoreMatchers.equalTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus.ACCEPTED
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.HttpStatus.CONFLICT
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.http.HttpStatus.NO_CONTENT
import org.springframework.http.HttpStatus.OK
import java.time.LocalDate

class UserIntegrationTest : BaseIntegrationTest() {
    @Autowired
    private lateinit var userRepository: IUserRepository

    @Qualifier("snakeCase")
    @Autowired
    private lateinit var mapper: ObjectMapper

    private val user = mockUser()

    @BeforeEach
    fun beforeEach() {
        userRepository.deleteAll()
    }

    @Test
    fun getByIdTest() {
        // GIVEN
        val createdUser = userRepository.saveAndFlush(user)

        // THEN
        `when`()
            .get("/users/${createdUser.id}")
            .then()
            .assertThat()
            .statusCode(equalTo(OK.value()))
            .and()
            .body("email", equalTo(user.email))
    }

    @Test
    fun getByIdThrowsNotFoundTest() {
        // THEN
        `when`()
            .get("/users/123")
            .then()
            .assertThat()
            .statusCode(equalTo(NOT_FOUND.value()))
    }

    @Test
    fun createUserTest() {
        // THEN
        given()
            .accept(JSON)
            .contentType(JSON)
            .body(
                mapper.writeValueAsBytes(user)
            )
            .`when`()
            .post("/users")
            .then()
            .assertThat()
            .statusCode(equalTo(CREATED.value()))
            .and()
            .body("email", equalTo(user.email))
    }

    @Test
    fun duplicatedUserExceptionTest() {
        // GIVEN
        val createdUser = userRepository.saveAndFlush(user)

        // THEN
        given()
            .accept(JSON)
            .contentType(JSON)
            .body(
                mapper.writeValueAsBytes(createdUser)
            )
            .`when`()
            .post("/users")
            .then()
            .assertThat()
            .statusCode(equalTo(CONFLICT.value()))
    }

    @Test
    fun deactivateUserByIdTest() {
        // GIVEN
        val createdUser = userRepository.saveAndFlush(user)

        // THEN
        `when`()
            .delete("/users/${createdUser.id}")
            .then()
            .assertThat()
            .statusCode(equalTo(NO_CONTENT.value()))

        assertThat {
            userRepository.findByIdOrNull(createdUser.id)
        }.isSuccess().isNotNull().isInactive()
    }

    @Test
    fun reactivateUserTest() {
        // GIVEN
        val createdUser = userRepository.saveAndFlush(
            mockUser(status = INACTIVE)
        )

        // THEN
        `when`()
            .put("/users/${createdUser.id}/reactivate")
            .then()
            .assertThat()
            .statusCode(equalTo(ACCEPTED.value()))
            .body("status", equalTo(ACTIVE.name))
    }

    @Test
    fun getByDefaultFiltersTest() {
        // GIVEN
        userRepository.saveAndFlush(user)

        // THEN
        `when`()
            .get("/users")
            .then()
            .assertThat()
            .statusCode(equalTo(OK.value()))
            .body("size()", equalTo(1))
    }

    @Test
    fun createUserYoungerThanEighteenTest() {
        // GIVEN
        val youngUser = mockUser(birthday = LocalDate.now())

        // THEN
        given()
            .accept(JSON)
            .contentType(JSON)
            .body(
                mapper.writeValueAsBytes(youngUser)
            )
            .`when`()
            .post("/users")
            .then()
            .assertThat()
            .statusCode(equalTo(BAD_REQUEST.value()))
    }

    @Test
    fun getOldestUsersTest() {
        // GIVEN
        val twentyThreeYearsOldUser = mockUser(id = 1, email = "toto@gmail.com", birthday = LocalDate.now().minusYears(23))
        val twentyYearsOldUser = mockUser(id = 2, email = "nano@gmail.com", birthday = LocalDate.now().minusYears(20))

        userRepository.saveAll(
            listOf(
                twentyThreeYearsOldUser,
                twentyYearsOldUser
            )
        )

        // THEN
        `when`()
            .get("/users/oldest")
            .then()
            .assertThat()
            .statusCode(equalTo(OK.value()))
            .body("size()", equalTo(1))
    }
}

private fun Assert<User>.isInactive() = given {
    if (it.status != INACTIVE) {
        expected("The user is active!")
    }
}

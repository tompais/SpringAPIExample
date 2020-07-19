package com.example.spring_api.unit.controllers

import assertk.all
import assertk.assertThat
import assertk.assertions.hasSize
import assertk.assertions.isEqualTo
import assertk.assertions.isNotEmpty
import com.example.spring_api.controllers.UserController
import com.example.spring_api.enums.Genre
import com.example.spring_api.enums.Genre.FEMALE
import com.example.spring_api.enums.Genre.MALE
import com.example.spring_api.enums.Genre.OTHER
import com.example.spring_api.models.User
import com.example.spring_api.models.User.Status
import com.example.spring_api.models.User.Status.ACTIVE
import com.example.spring_api.models.User.Status.INACTIVE
import com.example.spring_api.services.IUserService
import com.example.spring_api.utils.MockUtils.mockUser
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

@ExtendWith(MockKExtension::class)
class UserControllerTest {

    private companion object {
        @JvmStatic
        @Suppress("UNUSED")
        fun filters() = listOf(
            Arguments.of(
                ACTIVE,
                MALE,
                makeUsers(ACTIVE, MALE)
            ),
            Arguments.of(
                INACTIVE,
                FEMALE,
                makeUsers(INACTIVE, FEMALE)
            ),
            Arguments.of(
                INACTIVE,
                OTHER,
                makeUsers(INACTIVE, OTHER)
            ),
            Arguments.of(
                ACTIVE,
                null,
                makeUsers(INACTIVE)
            )
        )

        private fun makeUsers(status: Status, genre: Genre = OTHER, size: Int = 100): MutableList<User> {
            val users = mutableListOf<User>()

            repeat(size) {
                users.add(mockUser(id = it.toLong(), status = status, genre = genre))
            }

            return users
        }
    }

    @MockK
    private lateinit var userService: IUserService

    @InjectMockKs
    private lateinit var userController: UserController

    @ParameterizedTest
    @MethodSource("filters")
    fun getUsersByFiltersTest(status: Status, genre: Genre?, expectedUsers: List<User>) {
        // GIVEN
        every { userService.findAllByFilters(status, genre) } returns expectedUsers

        // WHEN
        val actualUsers = userController.getAllByFilters(status, genre)

        // THEN
        assertThat(actualUsers).all {
            isNotEmpty()
            hasSize(expectedUsers.size)
            isEqualTo(expectedUsers)
        }
    }

    @Test
    fun getUsersWithoutGenreParamTest() {
        // GIVEN
        val expectedUsers = makeUsers(INACTIVE, size = 10)
        every { userService.findAllByFilters(any(), any()) } returns expectedUsers

        // WHEN
        val actualUsers = userController.getAllByFilters(INACTIVE)

        // THEN
        assertThat(actualUsers).all {
            isNotEmpty()
            hasSize(expectedUsers.size)
            isEqualTo(expectedUsers)
        }
    }
}

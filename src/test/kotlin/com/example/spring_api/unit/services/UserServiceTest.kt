package com.example.spring_api.unit.services

import assertk.assertThat
import assertk.assertions.isFailure
import assertk.assertions.isInstanceOf
import com.example.spring_api.daos.IUserDAO
import com.example.spring_api.error.exceptions.UserNotFoundException
import com.example.spring_api.models.User.Status.INACTIVE
import com.example.spring_api.services.impl.UserService
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class UserServiceTest {
    @MockK
    private lateinit var userDAO: IUserDAO

    @InjectMockKs
    private lateinit var userService: UserService

    @Test
    fun reactivateThrowsUserNotFoundTest() {
        // GIVEN
        every { userDAO.findByIdAndStatus(any(), INACTIVE) } returns null

        // THEN
        assertThat {
            userService.reactivateById(1L)
        }.isFailure().isInstanceOf(UserNotFoundException::class)
    }
}

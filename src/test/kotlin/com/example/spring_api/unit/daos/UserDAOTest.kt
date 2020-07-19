package com.example.spring_api.unit.daos

import assertk.assertThat
import assertk.assertions.isEmpty
import com.example.spring_api.daos.impl.UserDAO
import com.example.spring_api.databases.repositories.IUserRepository
import com.example.spring_api.enums.Genre.MALE
import com.example.spring_api.models.User
import com.example.spring_api.models.User.Status.ACTIVE
import com.example.spring_api.utils.MockUtils.mockUser
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class UserDAOTest {
    @MockK
    private lateinit var userRepository: IUserRepository

    @InjectMockKs
    private lateinit var userDAO: UserDAO

    private val user = mockUser()

    @Test
    fun tryToDeactivateAnUserThatNotExistTest() {
        // GIVEN
        every { userRepository.findByIdAndStatus(any(), any()) } returns null

        // WHEN
        userDAO.deactivateById(user.id)

        // THEN
        verify { userRepository.findByIdAndStatus(any(), any()) }
        verify(exactly = 0) { userRepository.saveAndFlush(any<User>()) }
    }

    @Test
    fun findAllWithGenre() {
        // GIVEN
        every { userRepository.findAllByStatusAndGenre(any(), any()) } returns emptyList()

        // WHEN
        val users = userDAO.findAllByFilters(ACTIVE, MALE)

        // THEN
        assertThat(users).isEmpty()
    }
}

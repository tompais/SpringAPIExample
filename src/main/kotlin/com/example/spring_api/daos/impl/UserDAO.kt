package com.example.spring_api.daos.impl

import com.example.spring_api.daos.IUserDAO
import com.example.spring_api.databases.repositories.IUserRepository
import com.example.spring_api.enums.Genre
import com.example.spring_api.models.User
import com.example.spring_api.models.User.Status
import com.example.spring_api.models.User.Status.ACTIVE
import com.example.spring_api.models.User.Status.INACTIVE
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class UserDAO @Autowired constructor(
    val userRepository: IUserRepository
) : IUserDAO {
    override fun create(user: User): User = userRepository.saveAndFlush(user)

    override fun findByIdAndStatus(id: Long, status: Status): User? = userRepository.findByIdAndStatus(id, status)

    override fun deactivateById(id: Long) {
        findByIdAndStatus(id, ACTIVE)?.let {
            it.status = INACTIVE

            userRepository.saveAndFlush(it)
        }
    }

    override fun reactivate(user: User): User = userRepository.saveAndFlush(
        user.apply {
            status = ACTIVE
        }
    )

    override fun findAllByFilters(status: Status, genre: Genre?): List<User> = if (genre == null) {
        userRepository.findAllByStatus(status)
    } else {
        userRepository.findAllByStatusAndGenre(status, genre)
    }
}

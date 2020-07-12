package com.example.spring_api.services.impl

import com.example.spring_api.daos.IUserDAO
import com.example.spring_api.enums.Genre
import com.example.spring_api.error.exceptions.DuplicatedUserException
import com.example.spring_api.error.exceptions.UserNotFoundException
import com.example.spring_api.models.User
import com.example.spring_api.models.User.Status
import com.example.spring_api.requests.UserRequest
import com.example.spring_api.services.IUserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserService @Autowired constructor(
    val userDAO: IUserDAO
) : IUserService {
    override fun create(userRequest: UserRequest): User = try {
        userDAO.create(
            userRequest.buildModel()
        )
    } catch (e: DataIntegrityViolationException) {
        throw DuplicatedUserException(userRequest.email, e)
    }

    override fun findByIdAndActive(id: Long): User = userDAO.findByIdAndActive(id) ?: throw UserNotFoundException(id)

    override fun deactivateById(id: Long) = userDAO.deactivateById(id)

    override fun reactivate(id: Long): User {
        val user = userDAO.findById(id) ?: throw UserNotFoundException(id)

        return userDAO.reactivate(user)
    }

    override fun findAllByFilters(status: Status, genre: Genre?): List<User> = userDAO.findAllByFilters(status, genre)
}

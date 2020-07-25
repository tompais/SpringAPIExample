package com.example.spring_api.services

import com.example.spring_api.enums.Genre
import com.example.spring_api.models.User
import com.example.spring_api.models.User.Status
import com.example.spring_api.requests.UserRequest

interface IUserService {
    fun create(userRequest: UserRequest): User
    fun findByIdAndStatus(id: Long, status: Status): User
    fun deactivateById(id: Long)
    fun reactivateById(id: Long): User
    fun findAllByFilters(status: Status, genre: Genre?): List<User>
    fun findAllUsersOverTwentyYearsOld(): List<User>
}

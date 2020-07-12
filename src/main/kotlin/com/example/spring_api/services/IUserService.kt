package com.example.spring_api.services

import com.example.spring_api.enums.Genre
import com.example.spring_api.models.User
import com.example.spring_api.models.User.Status
import com.example.spring_api.requests.UserRequest

interface IUserService {
    fun create(userRequest: UserRequest): User
    fun findByIdAndActive(id: Long): User
    fun deactivateById(id: Long)
    fun reactivate(id: Long): User
    fun findAllByFilters(status: Status, genre: Genre?): List<User>
}

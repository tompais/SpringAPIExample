package com.example.spring_api.daos

import com.example.spring_api.enums.Genre
import com.example.spring_api.models.User
import com.example.spring_api.models.User.Status

interface IUserDAO {
    fun create(user: User): User
    fun findByIdAndActive(id: Long): User?
    fun findById(id: Long): User?
    fun deactivateById(id: Long)
    fun reactivate(user: User): User
    fun findAllByFilters(status: Status, genre: Genre?): List<User>
}

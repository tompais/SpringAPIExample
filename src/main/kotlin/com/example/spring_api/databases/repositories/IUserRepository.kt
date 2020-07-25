package com.example.spring_api.databases.repositories

import com.example.spring_api.enums.Genre
import com.example.spring_api.models.User
import com.example.spring_api.models.User.Status
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional
interface IUserRepository : JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    fun findByIdAndStatus(id: Long, status: Status): User?
    fun findAllByStatusAndGenre(status: Status, genre: Genre): List<User>
    fun findAllByStatus(status: Status): List<User>
}

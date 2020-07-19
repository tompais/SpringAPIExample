package com.example.spring_api.utils

import com.example.spring_api.enums.Genre
import com.example.spring_api.enums.Genre.OTHER
import com.example.spring_api.models.User
import com.example.spring_api.models.User.Status
import com.example.spring_api.models.User.Status.ACTIVE
import java.text.SimpleDateFormat
import java.util.Date

object MockUtils {
    fun mockUser(
        id: Long = 1,
        firstName: String = "Pepe",
        lastName: String = "Martínez",
        email: String = "pepe.martinez@gmail.com",
        birthday: Date = SimpleDateFormat("yyyy-MM-dd").parse("1995-11-15"),
        genre: Genre = OTHER,
        status: Status = ACTIVE
    ) = User(id, firstName, lastName, email, birthday, genre, status)
}

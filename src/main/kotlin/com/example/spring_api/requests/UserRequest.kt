package com.example.spring_api.requests

import com.example.spring_api.enums.Genre
import com.example.spring_api.models.User
import java.util.Date
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Past

class UserRequest(
    @field:NotBlank
    val firstName: String,

    @field:NotBlank
    val lastName: String,

    @field:NotBlank
    @field:Email
    val email: String,

    @field:Past
    val birthday: Date,

    private val genre: Genre?
) : IModelBuilder<User> {
    override fun buildModel() = User(
        id = 0,
        firstName = firstName,
        lastName = lastName,
        email = email,
        birthday = birthday,
        genre = genre ?: Genre.OTHER
    )
}

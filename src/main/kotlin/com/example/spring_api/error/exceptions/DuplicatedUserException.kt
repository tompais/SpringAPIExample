package com.example.spring_api.error.exceptions

class DuplicatedUserException(email: String, cause: Throwable? = null) :
    ConflictException("The user with email [$email] is duplicated", cause)

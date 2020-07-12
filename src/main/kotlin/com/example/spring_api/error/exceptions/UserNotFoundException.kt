package com.example.spring_api.error.exceptions

class UserNotFoundException(id: Long) : NotFoundException("The user with id [$id] wasn't found")

package com.example.spring_api.error.exceptions

import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(NOT_FOUND)
open class NotFoundException(
    message: String
) : APIException(message, NOT_FOUND)

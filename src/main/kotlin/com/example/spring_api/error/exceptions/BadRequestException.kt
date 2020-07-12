package com.example.spring_api.error.exceptions

import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(BAD_REQUEST)
class BadRequestException(
    message: String,
    cause: Throwable? = null
) : APIException(message, BAD_REQUEST, cause)

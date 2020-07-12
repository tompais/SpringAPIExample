package com.example.spring_api.error.exceptions

import org.springframework.http.HttpStatus.CONFLICT
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(CONFLICT)
open class ConflictException(
    message: String,
    cause: Throwable? = null
) : APIException(message, CONFLICT, cause)

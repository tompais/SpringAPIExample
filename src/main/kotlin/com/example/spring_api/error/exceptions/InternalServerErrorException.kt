package com.example.spring_api.error.exceptions

import org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(INTERNAL_SERVER_ERROR)
open class InternalServerErrorException(message: String, cause: Throwable? = null) :
    APIException(message, INTERNAL_SERVER_ERROR, cause)

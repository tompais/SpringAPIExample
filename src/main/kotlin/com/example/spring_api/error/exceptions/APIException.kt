package com.example.spring_api.error.exceptions

import com.example.spring_api.configs.Slf4jMDCFilterConfiguration
import org.slf4j.MDC
import org.springframework.http.HttpStatus

open class APIException(
    message: String,
    val status: HttpStatus,
    cause: Throwable? = null,
    val requestId: String = MDC.get(Slf4jMDCFilterConfiguration.DEFAULT_MDC_UUID_TOKEN_KEY)
) : RuntimeException(
    message,
    cause
)

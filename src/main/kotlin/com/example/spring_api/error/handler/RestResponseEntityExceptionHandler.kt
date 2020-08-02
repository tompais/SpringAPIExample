package com.example.spring_api.error.handler

import com.example.spring_api.error.exceptions.APIException
import com.example.spring_api.error.exceptions.BadRequestException
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.databind.exc.MismatchedInputException
import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import org.apache.commons.lang3.exception.ExceptionUtils
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.util.Date
import javax.validation.ConstraintViolationException

@RestControllerAdvice
class RestResponseEntityExceptionHandler : ResponseEntityExceptionHandler() {

    fun logException(exception: Exception) {
        logger.error(exception.message, exception)
    }

    private fun getExceptionParameter(e: JsonMappingException): String =
        e.path.joinToString(".") {
            if (it.index >= 0)
                "[${it.index}]"
            else it.fieldName
        }

    override fun handleHttpMessageNotReadable(
        ex: HttpMessageNotReadableException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> = handleBadRequestException(
        ex.message ?: "error in reading body",
        ex
    )

    @ExceptionHandler(MissingKotlinParameterException::class)
    fun handleMissingKotlinParameterException(missingKotlinParameterException: MissingKotlinParameterException): ResponseEntity<Any> =
        handleBadRequestException(
            "key [${getExceptionParameter(missingKotlinParameterException)}] not found",
            missingKotlinParameterException
        )

    @ExceptionHandler(MismatchedInputException::class)
    fun handleMismatchedInputException(mismatchedInputException: MismatchedInputException): ResponseEntity<Any> =
        handleBadRequestException(
            "type mismatch for key [${getExceptionParameter(mismatchedInputException)}]",
            mismatchedInputException
        )

    @ExceptionHandler(JsonProcessingException::class)
    fun handleJsonProcessingException(jsonProcessingException: JsonProcessingException): ResponseEntity<Any> =
        handleBadRequestException(
            "error in parsing body",
            jsonProcessingException
        )

    private fun handleBadRequestException(
        message: String,
        exception: Exception
    ): ResponseEntity<Any> = handleAPIException(BadRequestException(message, exception))

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> = handleBadRequestException(
        ex.message,
        ex
    )

    @ExceptionHandler(ConstraintViolationException::class)
    fun handleConstraintViolationException(constraintViolationException: ConstraintViolationException): ResponseEntity<Any> =
        handleBadRequestException(
            constraintViolationException.message ?: "Unrecognized constraint violation",
            constraintViolationException
        )

    @ExceptionHandler(APIException::class)
    fun handleAPIException(exception: APIException): ResponseEntity<Any> {
        logException(exception)

        return ResponseEntity.status(
            exception.status.value()
        ).body(
            mapOf(
                "message" to exception.message,
                "status_code" to exception.status.value(),
                "error_code" to exception.status.reasonPhrase,
                "timestamp" to Date(),
                "original_exception" to ExceptionUtils.getRootCauseMessage(exception),
                "request_id" to exception.requestId,
                "stack_trace" to ExceptionUtils.getStackFrames(exception).map { it.replace("\t", "") }
            )
        )
    }
}

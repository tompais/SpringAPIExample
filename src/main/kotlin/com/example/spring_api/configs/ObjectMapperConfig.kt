package com.example.spring_api.configs

import com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES
import com.fasterxml.jackson.databind.DeserializationFeature.READ_ENUMS_USING_TO_STRING
import com.fasterxml.jackson.databind.MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SNAKE_CASE
import com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS
import com.fasterxml.jackson.databind.SerializationFeature.WRITE_ENUMS_USING_TO_STRING
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import java.util.TimeZone

@Configuration
class ObjectMapperConfig {
    @Bean
    @Primary
    fun snakeCase(): ObjectMapper = jacksonObjectMapper()
        .registerModule(JavaTimeModule())
        .enable(WRITE_ENUMS_USING_TO_STRING)
        .enable(READ_ENUMS_USING_TO_STRING)
        .enable(ACCEPT_CASE_INSENSITIVE_ENUMS)
        .disable(WRITE_DATES_AS_TIMESTAMPS)
        .disable(FAIL_ON_UNKNOWN_PROPERTIES)
        .setTimeZone(TimeZone.getTimeZone("UTC"))
        .setPropertyNamingStrategy(SNAKE_CASE)
}

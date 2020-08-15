package com.example.spring_api.enums

import com.fasterxml.jackson.annotation.JsonValue

enum class Genre {
    MALE,
    FEMALE,
    OTHER;

    @JsonValue
    override fun toString(): String {
        return name.toLowerCase()
    }
}

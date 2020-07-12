package com.example.spring_api.databases

import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.util.UriComponentsBuilder
import javax.validation.constraints.NotBlank

open class DatabaseInfo(
    val databaseType: DatabaseType,
    val host: String,
    val databaseName: String,
    val username: String,
    val password: String,
    val extraParams: Map<String, String>? = null
) {
    enum class DatabaseType(
        @NotBlank
        val driver: String,

        @NotBlank
        val dialect: String
    ) {
        MYSQL(
            "com.mysql.cj.jdbc.Driver",
            "org.hibernate.dialect.MySQL8Dialect"
        ),
        H2(
            "org.h2.Driver",
            "org.hibernate.dialect.H2Dialect"
        );

        fun buildUrl(host: String, databaseName: String, extraParams: Map<String, String>? = null): String {
            return UriComponentsBuilder.newInstance()
                .scheme("jdbc:${name.toLowerCase()}")
                .host(host)
                .path(databaseName)
                .queryParams(
                    extraParams.toMultiValueMap()
                )
                .build()
                .toUriString()
        }
    }
}

private fun <K, V> Map<K, V>?.toMultiValueMap(): MultiValueMap<K, V>? {
    return this?.map {
        it.key to listOf(
            it.value
        )
    }?.toMap()?.let {
        LinkedMultiValueMap(
            it
        )
    }
}

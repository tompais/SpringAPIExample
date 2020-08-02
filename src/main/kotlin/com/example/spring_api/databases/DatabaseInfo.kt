package com.example.spring_api.databases

import com.example.spring_api.error.exceptions.InvalidMySQLVersion
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
        MYSQL5(
            "com.mysql.jdbc.Driver",
            "org.hibernate.dialect.MySQL5Dialect"
        ) {
            override fun toString() = super.toString().removeNumbers()
        },
        MYSQL8(
            "com.mysql.cj.jdbc.Driver",
            "org.hibernate.dialect.MySQL8Dialect"
        ) {
            override fun toString() = super.toString().removeNumbers()
        },
        H2(
            "org.h2.Driver",
            "org.hibernate.dialect.H2Dialect"
        ) {
            override fun buildUrl(host: String, databaseName: String, extraParams: Map<String, String>?): String =
                "jdbc:${toString()}:$host:$databaseName"
        };

        companion object {
            fun getMySQLType(mysqlVersion: Int) = when (mysqlVersion) {
                5 -> MYSQL5
                8 -> MYSQL8
                else -> throw InvalidMySQLVersion(mysqlVersion)
            }
        }

        open fun buildUrl(host: String, databaseName: String, extraParams: Map<String, String>? = null): String {
            return UriComponentsBuilder.newInstance()
                .scheme("jdbc:${toString()}")
                .host(host)
                .path(databaseName)
                .queryParams(
                    extraParams.toMultiValueMap()
                )
                .build()
                .toUriString()
        }

        override fun toString() = name.toLowerCase()
    }
}

private fun String.removeNumbers() = this.replace(Regex("[0-9]"), "")

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

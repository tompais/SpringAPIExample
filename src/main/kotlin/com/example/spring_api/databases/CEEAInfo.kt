package com.example.spring_api.databases

import com.example.spring_api.databases.HibernateInfo.HibernateMode.CREATE_DROP
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Positive

@Profile("prod")
@Component("CEEAInfo")
class CEEAInfo @Autowired constructor(
    @Positive @Value("\${MYSQL_VERSION:8}") mysqlVersion: Int,
    @NotBlank @Value("\${CEEA_DB_URL:localhost:3306}") host: String,
    @NotBlank @Value("\${CEEA_DB_SCHEMA:ceea}") databaseName: String,
    @NotBlank @Value("\${CEEA_DB_USER:root}") username: String,
    @NotBlank @Value("\${CEEA_DB_PASSWORD:root1234}") password: String,
    @NotBlank @Value("\${CEEA_DB_PARAMS:useSSL=false}") params: String
) : HikariInfo(
    DatabaseType.getMySQLType(mysqlVersion),
    host,
    databaseName,
    username,
    password,
    buildExtraParamsMap(params),
    hibernateMode = CREATE_DROP
)

fun buildExtraParamsMap(params: String): Map<String, String>? = params.split('&').associate {
    val (left, right) = it.split('=')
    left to right
}

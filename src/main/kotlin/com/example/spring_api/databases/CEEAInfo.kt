package com.example.spring_api.databases

import com.example.spring_api.databases.DatabaseInfo.DatabaseType.MYSQL
import com.example.spring_api.databases.HibernateInfo.HibernateMode.CREATE_DROP
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Profile("prod")
@Component("CEEAInfo")
class CEEAInfo : HikariInfo(
    MYSQL,
    "localhost:3306",
    "ceea",
    "root",
    "root1234",
    mapOf(
        "useSSL" to "false"
    ),
    hibernateMode = CREATE_DROP
)

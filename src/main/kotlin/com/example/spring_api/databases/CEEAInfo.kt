package com.example.spring_api.databases

import com.example.spring_api.databases.DatabaseInfo.DatabaseType.MYSQL
import com.example.spring_api.databases.HibernateInfo.HibernateMode.CREATE
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Profile("prod")
@Component("CEEAInfo")
class CEEAInfo : HikariInfo(
    MYSQL,
    "b5b688f48d59fd:f606d19c@us-cdbr-east-02.cleardb.com",
    "heroku_64f3f9f2ce5afac",
    "b5b688f48d59fd",
    "f606d19c",
    mapOf(
        "reconnect" to "true"
    ),
    hibernateMode = CREATE
)

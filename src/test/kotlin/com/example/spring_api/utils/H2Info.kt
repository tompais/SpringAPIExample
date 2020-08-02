package com.example.spring_api.utils

import com.example.spring_api.databases.DatabaseInfo.DatabaseType.H2
import com.example.spring_api.databases.HibernateInfo.HibernateMode.CREATE_DROP
import com.example.spring_api.databases.HikariInfo

open class H2Info : HikariInfo(
    H2,
    "mem",
    "test",
    "sa",
    "",
    hibernateMode = CREATE_DROP
)

package com.example.spring_api.databases

import com.example.spring_api.databases.HibernateInfo.HibernateMode.UPDATE

open class HibernateInfo(
    databaseType: DatabaseType,
    host: String,
    databaseName: String,
    username: String,
    password: String,
    extraParams: Map<String, String>? = null,
    val hibernateMode: HibernateMode = UPDATE
) : DatabaseInfo(databaseType, host, databaseName, username, password, extraParams) {
    enum class HibernateMode {
        CREATE,
        CREATE_DROP,
        UPDATE;

        override fun toString() = name.toLowerCase().replace("_", "-")
    }
}

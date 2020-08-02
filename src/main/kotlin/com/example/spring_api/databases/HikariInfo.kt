package com.example.spring_api.databases

import com.example.spring_api.databases.HibernateInfo.HibernateMode.UPDATE
import com.zaxxer.hikari.HikariConfig

open class HikariInfo(
    databaseType: DatabaseType,
    host: String,
    databaseName: String,
    username: String,
    password: String,
    extraParams: Map<String, String>? = null,
    hibernateMode: HibernateMode = UPDATE,
    private val minimumIdle: Int = 5,
    private val maximumPoolSize: Int = 5,
    private val idleTimeout: Long = 30000,
    private val poolName: String = "SpringBootJPAHikariCP",
    private val maxLifetime: Long = 2000000,
    private val connectionTimeout: Long = 30000
) : HibernateInfo(databaseType, host, databaseName, username, password, extraParams, hibernateMode) {
    fun buildConfig(): HikariConfig {
        val config = HikariConfig()

        config.jdbcUrl = databaseType.buildUrl(host, databaseName, extraParams)
        config.username = username
        config.password = password
        config.driverClassName = databaseType.driver
        config.minimumIdle = minimumIdle
        config.maximumPoolSize = maximumPoolSize
        config.idleTimeout = idleTimeout
        config.poolName = poolName
        config.maxLifetime = maxLifetime
        config.connectionTimeout = connectionTimeout

        return config
    }
}

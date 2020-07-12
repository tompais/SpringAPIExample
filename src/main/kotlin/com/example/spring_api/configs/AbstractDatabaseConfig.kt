package com.example.spring_api.configs

import com.example.spring_api.databases.HikariInfo
import com.zaxxer.hikari.HikariDataSource
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.PlatformTransactionManager
import javax.sql.DataSource

abstract class AbstractDatabaseConfig(
    private val hikariInfo: HikariInfo
) {
    fun makeEntityManager() = LocalContainerEntityManagerFactoryBean().apply {
        dataSource = makeDataSource()
        jpaVendorAdapter = HibernateJpaVendorAdapter()
    }.also {
        it.setPackagesToScan(
            "com.example.spring_api.models"
        )
        it.setJpaPropertyMap(
            mapOf(
                "hibernate.hbm2ddl.auto" to hikariInfo.hibernateMode.toString(),
                "hibernate.dialect" to hikariInfo.databaseType.dialect,
                "hibernate.show_sql" to true,
                "hibernate.format_sql" to true,
                "hibernate.use_sql" to true,
                "hibernate.id.new_generator_mappings" to false,
                "hibernate.search.autoregister_listeners" to false,
                "hibernate.bytecode.use_reflection_optimizer" to false
            )
        )
    }

    fun makeDataSource(): DataSource = HikariDataSource(
        hikariInfo.buildConfig()
    )

    fun makePlatformTransactionManager(): PlatformTransactionManager {
        return JpaTransactionManager().apply {
            entityManagerFactory = makeEntityManager().getObject()
        }
    }
}

package com.example.spring_api.configs

import com.example.spring_api.databases.HikariInfo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.transaction.PlatformTransactionManager
import javax.sql.DataSource

@Configuration
@EnableJpaRepositories(
    basePackages = ["com.example.spring_api.databases.repositories"],
    entityManagerFactoryRef = "ceeaEntityManager",
    transactionManagerRef = "ceeaTransactionManager"
)
class CEEADatabaseConfig @Autowired constructor(@Qualifier("CEEAInfo") hikariInfo: HikariInfo) :
    AbstractDatabaseConfig(hikariInfo) {

    @Primary
    @Bean
    fun ceeaEntityManager(): LocalContainerEntityManagerFactoryBean = makeEntityManager()

    @Primary
    @Bean
    fun ceeaDataSource(): DataSource = makeDataSource()

    @Primary
    @Bean
    fun ceeaTransactionManager(): PlatformTransactionManager = makePlatformTransactionManager()
}

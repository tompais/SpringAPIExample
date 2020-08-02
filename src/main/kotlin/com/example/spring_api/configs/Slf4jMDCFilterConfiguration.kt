package com.example.spring_api.configs

import com.example.spring_api.filters.Slf4jMDCFilter
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "config.slf4jfilter")
class Slf4jMDCFilterConfiguration {
    companion object {
        private const val DEFAULT_RESPONSE_TOKEN_HEADER = "Response_Token"
        const val DEFAULT_MDC_UUID_TOKEN_KEY = "Slf4jMDCFilter.UUID"
    }

    @Bean
    fun servletRegistrationBean() = FilterRegistrationBean<Slf4jMDCFilter>().apply {
        filter = Slf4jMDCFilter(DEFAULT_RESPONSE_TOKEN_HEADER, DEFAULT_MDC_UUID_TOKEN_KEY)
        order = 2
    }
}

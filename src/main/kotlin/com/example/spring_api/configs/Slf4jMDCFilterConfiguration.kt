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

    private val responseHeader = DEFAULT_RESPONSE_TOKEN_HEADER
    private val mdcTokenKey = DEFAULT_MDC_UUID_TOKEN_KEY
    private val requestHeader: String? = null

    @Bean
    fun servletRegistrationBean(): FilterRegistrationBean<*>? {
        return FilterRegistrationBean<Slf4jMDCFilter>().apply {
            filter = Slf4jMDCFilter(responseHeader, mdcTokenKey, requestHeader)
            order = 2
        }
    }
}

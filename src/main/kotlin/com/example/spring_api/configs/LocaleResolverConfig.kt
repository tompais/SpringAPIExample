package com.example.spring_api.configs

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.i18n.SessionLocaleResolver
import java.util.Locale.ENGLISH

@Configuration
class LocaleResolverConfig {
    @Bean
    fun localeResolver() = SessionLocaleResolver().also {
        it.setDefaultLocale(ENGLISH)
    }
}

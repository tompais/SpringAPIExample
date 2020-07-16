package com.example.spring_api.filters

import org.slf4j.MDC
import org.springframework.web.filter.OncePerRequestFilter
import java.util.UUID
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class Slf4jMDCFilter(
    private val responseHeader: String,
    private val mdcTokenKey: String
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            val token = UUID.randomUUID().toString().toUpperCase().replace("-", "")

            MDC.put(mdcTokenKey, token)

            if (!responseHeader.isBlank()) {
                response.addHeader(responseHeader, token)
            }

            filterChain.doFilter(request, response)
        } finally {
            MDC.remove(mdcTokenKey)
        }
    }
}

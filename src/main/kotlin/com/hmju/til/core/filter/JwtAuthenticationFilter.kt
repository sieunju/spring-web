package com.hmju.til.core.filter

import com.hmju.til.component.JwtComponent
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.GenericFilterBean

/**
 * Description : JWT AUTH Filter
 *
 * Created by juhongmin on 1/16/24
 */
internal class JwtAuthenticationFilter(
    private val jwtComponent: JwtComponent
) : GenericFilterBean() {

    override fun doFilter(
        req: ServletRequest,
        res: ServletResponse,
        chain: FilterChain
    ) {
        if (req !is HttpServletRequest) {
            chain.doFilter(req, res)
            return
        }
        val token = jwtComponent.getHeaderToken(req)
        // 유효한 경우
        if (token != null && jwtComponent.isValidate(token)) {
            val auth = jwtComponent.getAuthentication(token)
            logger.info("Auth ${auth.isAuthenticated} ${auth.principal}")
            SecurityContextHolder.getContext().authentication = auth
        }
        chain.doFilter(req, res)
    }
}
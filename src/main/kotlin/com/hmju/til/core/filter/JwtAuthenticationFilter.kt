package com.hmju.til.core.filter

import com.hmju.til.component.JwtComponent
import com.hmju.til.core.exception.ExpiredAuthException
import com.hmju.til.core.exception.InvalidAuthException
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
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
        try {
            val token = jwtComponent.getHeaderToken(req)
            // 만료된 토큰이 아닌경우
            if (token != null && !jwtComponent.isExpired(token)) {
                SecurityContextHolder.getContext().authentication = jwtComponent.getAuthentication(token)
            }
        } catch (ex: ExpiredAuthException) {
            if (res !is HttpServletResponse) return
            ex.sendErrorBody(res)
            return
        } catch (ex: InvalidAuthException) {
            if (res !is HttpServletResponse) return
            ex.sendErrorBody(res)
            return
        }
        chain.doFilter(req, res)
    }
}

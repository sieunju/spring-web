package com.hmju.til.core.filter

import com.fasterxml.jackson.databind.ObjectMapper
import com.hmju.til.component.JwtComponent
import com.hmju.til.core.exception.InvalidJwtException
import com.hmju.til.core.model.JSendMeta
import com.hmju.til.core.model.JSendResponse
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
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
            // 유효한 경우
            if (token != null && jwtComponent.isValidate(token)) {
                SecurityContextHolder.getContext().authentication = jwtComponent.getAuthentication(token)
            }
        } catch (ex: InvalidJwtException) {
            if (res is HttpServletResponse) {
                res.status = 401
                res.contentType = "application/json"
                val mapper = ObjectMapper()
                val errorJson = mapper.writeValueAsString(
                    JSendResponse.Builder<Any, JSendMeta>()
                        .setPayload(ex.msg).getBody()
                )
                res.writer.write(errorJson)
            }
            return
        }
        chain.doFilter(req, res)
    }
}

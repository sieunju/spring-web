package com.hmju.til.component

import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.core.Authentication

/**
 * Description : Json Web Token Component
 *
 * Created by juhongmin on 1/16/24
 */
interface JwtComponent {
    fun createToken(userEmail: String): String

    fun getHeaderToken(
        req: HttpServletRequest
    ): String?

    fun isValidate(token: String): Boolean

    fun getAuthentication(token: String) : Authentication
}

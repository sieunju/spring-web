package com.hmju.til.component

import com.hmju.til.features.auth_jwt.model.entity.AuthEntity
import com.hmju.til.features.auth_jwt.model.vo.AuthVo
import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.core.Authentication

/**
 * Description : Json Web Token Component
 *
 * Created by juhongmin on 1/16/24
 */
interface JwtComponent {

    fun createToken(vo: AuthVo): AuthEntity

    fun getHeaderToken(
        req: HttpServletRequest
    ): String?

    fun isValidate(token: String): Boolean

    fun isExpired(token: String) : Boolean

    fun getAuthentication(token: String): Authentication
}

package com.hmju.til.component

import com.hmju.til.features.auth_jwt.model.entity.JsonWebToken
import com.hmju.til.features.auth_jwt.model.entity.JwtInfo
import com.hmju.til.features.auth_jwt.model.vo.AuthVo
import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.core.Authentication

/**
 * Description : Json Web Token Component
 *
 * Created by juhongmin on 1/16/24
 */
interface JwtComponent {
    fun create(vo: AuthVo): JwtInfo

    fun create(entity: JsonWebToken): JwtInfo

    fun isTokenValidate(auth: String): Boolean

    fun getHeaderToken(req: HttpServletRequest): String?

    fun getHeaderToken(auth: String): String

    fun isValidate(token: String): Boolean

    fun isExpired(token: String): Boolean

    fun getAuthentication(token: String): Authentication
}
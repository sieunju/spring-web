package com.hmju.til.features.auth_jwt.model.dto

import com.hmju.til.features.auth_jwt.model.entity.JwtInfo
import java.time.LocalDateTime
import java.util.*

/**
 * Description : JsonWebToken Entity DTO
 *
 * Created by juhongmin on 1/21/24
 */
data class JsonWebTokenDTO(
    val id: Long? = null,
    val email: String,
    val accessToken: String,
    val accessExpiredAt: LocalDateTime,
    val refreshToken: String,
    val refreshExpiredAt: LocalDateTime,
) {
    constructor(
        info: JwtInfo,
    ) : this(
        email = info.email,
        accessToken = info.token,
        accessExpiredAt = info.expiredAt,
        refreshToken = info.refreshToken,
        refreshExpiredAt = info.refreshExpiredAt,
    )
}
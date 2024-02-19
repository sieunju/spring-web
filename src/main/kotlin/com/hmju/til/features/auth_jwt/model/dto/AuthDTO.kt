package com.hmju.til.features.auth_jwt.model.dto

import com.hmju.til.features.auth_jwt.model.entity.JwtInfo
import java.time.LocalDateTime

/**
 * Description : Auth Client 전용 DTO
 *
 * Created by juhongmin on 1/27/24
 */
data class AuthDTO(
    val accessToken: String,
    val accessExpiredAt: LocalDateTime,
    val refreshToken: String,
    val refreshExpiredAt: LocalDateTime,
) {
    constructor(
        info: JwtInfo,
    ) : this(
        accessToken = info.token,
        accessExpiredAt = info.expiredAt,
        refreshToken = info.refreshToken,
        refreshExpiredAt = info.refreshExpiredAt,
    )

    constructor(dto: JsonWebTokenDTO) : this(
        accessToken = dto.accessToken,
        accessExpiredAt = dto.accessExpiredAt,
        refreshToken = dto.refreshToken,
        refreshExpiredAt = dto.refreshExpiredAt,
    )
}
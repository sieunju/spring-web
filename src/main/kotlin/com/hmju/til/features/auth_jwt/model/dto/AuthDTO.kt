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
    val accessExpiredDate: LocalDateTime,
    val refreshToken: String,
    val refreshExpiredDate: LocalDateTime
) {
    constructor(
        info: JwtInfo
    ) : this(
        accessToken = info.token,
        accessExpiredDate = info.expiredDate,
        refreshToken = info.refreshToken,
        refreshExpiredDate = info.refreshExpiredDate
    )

    constructor(dto: JsonWebTokenDTO) : this(
        accessToken = dto.accessToken,
        accessExpiredDate = dto.accessExpiredDate,
        refreshToken = dto.refreshToken,
        refreshExpiredDate = dto.refreshExpiredDate
    )
}

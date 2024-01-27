package com.hmju.til.features.auth_jwt.model.dto

import com.hmju.til.features.auth_jwt.model.entity.JwtInfo
import java.time.LocalDateTime
import java.util.*

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
}

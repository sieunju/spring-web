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
    val token: String,
    val expiredDate: LocalDateTime,
    val refreshToken: String,
    val refreshExpiredDate: LocalDateTime
) {

    constructor(
        info: JwtInfo
    ) : this(
        email = info.email,
        token = info.token,
        expiredDate = info.expiredDate,
        refreshToken = info.refreshToken,
        refreshExpiredDate = info.refreshExpiredDate
    )

    constructor(
        id: Long,
        info: JwtInfo
    ) : this(
        id = id,
        email = info.email,
        token = info.token,
        expiredDate = info.expiredDate,
        refreshToken = info.refreshToken,
        refreshExpiredDate = info.refreshExpiredDate
    )
}

package com.hmju.til.features.auth_jwt.model.entity

import java.time.LocalDateTime
import java.util.*

/**
 * Description : JWT Component 에서 전달 받는 데이터 모델
 *
 * Created by juhongmin on 1/21/24
 */
data class JwtInfo(
    val token: String = "",
    val expiredDate: LocalDateTime,
    val email: String = "",
    val refreshToken: String = "",
    val refreshExpiredDate: LocalDateTime
)

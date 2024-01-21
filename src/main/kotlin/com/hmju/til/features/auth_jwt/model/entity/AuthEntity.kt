package com.hmju.til.features.auth_jwt.model.entity

import java.util.Date

/**
 * Description : JWT Auth Entity
 *
 * Created by juhongmin on 1/21/24
 */
data class AuthEntity(
    val token: String = "",
    val refreshToken: String = "",
    val expiredDate: Date
)

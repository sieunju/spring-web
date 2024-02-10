package com.hmju.til.features.auth_jwt.model.vo

/**
 * Description : JWT Request Body
 *
 * Created by juhongmin on 1/21/24
 */
data class AuthVo(
    val email: String = "",
    val expiredMinute: Int = 5 // 5
)

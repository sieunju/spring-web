package com.hmju.til.core.exception

import org.springframework.http.HttpHeaders

/**
 * Description :
 *
 * Created by juhongmin on 1/19/24
 */
data class InvalidJwtException(
    val auth: String,
    val msg: String = "Invalid ${HttpHeaders.AUTHORIZATION} $auth"
) : RuntimeException(msg)

package com.hmju.til.features.auth_jwt_test.model

/**
 * Description : JWT 성능 체크 용 데이터 모델
 *
 * Created by juhongmin on 2/18/24
 */
data class JwtPerformanceTestDTO(
    val message: String = "",
    val takeTimeMs: Long = 0
) {
    constructor(startTime: Long) : this(
        message = "Msg",
        takeTimeMs = System.currentTimeMillis().minus(startTime)
    )
}

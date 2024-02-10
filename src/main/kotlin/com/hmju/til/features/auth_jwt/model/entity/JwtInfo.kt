package com.hmju.til.features.auth_jwt.model.entity

import com.hmju.til.toLocalDateTime
import java.time.LocalDateTime
import java.util.*

/**
 * Description : JWT Component 에서 전달 받는 데이터 모델
 *
 * Created by juhongmin on 1/21/24
 */
class JwtInfo private constructor(
    builder: Builder
) {

    val email: String
    val token: String
    val expiredDate: LocalDateTime
    val refreshToken: String
    val refreshExpiredDate: LocalDateTime

    init {
        email = builder.email
        token = builder.accessToken
        expiredDate = builder.accessTokenExpiredAt!!
        refreshToken = builder.refreshToken
        refreshExpiredDate = builder.refreshTokenExpiredAt!!
    }

    class Builder {
        var email: String = ""
            private set
        var accessToken: String = ""
            private set
        var accessTokenExpiredAt: LocalDateTime? = null
            private set
        var refreshToken: String = ""
            private set
        var refreshTokenExpiredAt: LocalDateTime? = null
            private set

        fun setEmail(email: String): Builder {
            this.email = email
            return this
        }

        fun setToken(pair: Pair<String, Date>): Builder {
            accessToken = pair.first
            accessTokenExpiredAt = pair.second.toLocalDateTime()
            return this
        }

        fun setRefreshToken(pair: Pair<String, Date>): Builder {
            refreshToken = pair.first
            refreshTokenExpiredAt = pair.second.toLocalDateTime()
            return this
        }

        fun build(): JwtInfo {
            if (email.isEmpty() ||
                accessToken.isEmpty() ||
                refreshToken.isEmpty()
            ) throw IllegalArgumentException("Token 정보가 부족합니다.")
            return JwtInfo(this)
        }
    }
}

package com.hmju.til.features.auth_jwt.model.entity

import com.hmju.til.features.auth_jwt.model.dto.JsonWebTokenDTO
import jakarta.persistence.*
import java.time.LocalDateTime

/**
 * Description : JsonWebToken (RefreshToken) 을 가지고 있는 Entity
 *
 * Created by juhongmin on 1/21/24
 */
@Entity
@Table(name = "JWT_TB")
data class JsonWebToken(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    val id: Long = 0,
    @Column(name = "EMAIL", nullable = false)
    val email: String = "",
    @Column(name = "REFRESH_TOKEN", nullable = false)
    val token: String = "",
    @Column(name = "REFRESH_TOKEN_EXPIRED_DATE", nullable = false)
    val expiredDate: LocalDateTime = LocalDateTime.now(),
    @Column(name = "REGISTER_DATE", nullable = false)
    val registerDate: LocalDateTime = LocalDateTime.now(),
    @Column(name = "IS_DELETE", nullable = false)
    val isDelete: Boolean = false // true 인 경우 하루뒤 삭제하는 스크립트 작성 예정
) {
    constructor(dto: JsonWebTokenDTO) : this(
        id = dto.id ?: 0,
        email = dto.email,
        token = dto.refreshToken,
        expiredDate = dto.refreshExpiredAt,
        registerDate = LocalDateTime.now()
    )

    constructor(info: JwtInfo) : this(
        id = 0,
        email = info.email,
        token = info.refreshToken,
        expiredDate = info.refreshExpiredAt,
        registerDate = LocalDateTime.now()
    )
}

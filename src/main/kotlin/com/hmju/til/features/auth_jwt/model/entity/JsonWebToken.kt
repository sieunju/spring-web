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
    @Column(name = "TOKEN", nullable = false)
    val token: String = "",
    @Column(name = "EXPIRED_DATE", nullable = false)
    val expiredDate: LocalDateTime = LocalDateTime.now(),
    @Column(name = "REGISTER_DATE", nullable = false)
    val registerDate: LocalDateTime = LocalDateTime.now()
) {
    constructor(dto: JsonWebTokenDTO) : this(
        id = dto.id ?: 0,
        email = dto.email,
        token = dto.refreshToken,
        expiredDate = dto.expiredDate,
        registerDate = LocalDateTime.now()
    )
}

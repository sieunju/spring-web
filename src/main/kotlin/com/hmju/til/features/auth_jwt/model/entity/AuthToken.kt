package com.hmju.til.features.auth_jwt.model.entity

import jakarta.persistence.*
import java.time.LocalDateTime

/**
 * Description : JWT Token Entity
 *
 * Created by juhongmin on 1/21/24
 */
@Entity
@Table(name = "AUTH_TOKEN_TB")
data class AuthToken(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    val id: Long = 0,
    @Column(name = "TOKEN", nullable = false)
    val token: String,
    @Column(name = "EXPIRED_DATE", nullable = false)
    val registerDate: LocalDateTime
)

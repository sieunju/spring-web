package com.hmju.til.features.auth_jwt

import com.hmju.til.features.auth_jwt.model.entity.JsonWebToken
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

/**
 * Description : Jwt Auth Repository
 *
 * Created by juhongmin on 1/27/24
 */
@Suppress("unused")
interface JwtAuthRepository : JpaRepository<JsonWebToken, Long> {
    @Query(
        value = "SELECT *\n" +
                "\t\tFROM JWT_TB " +
                "WHERE TOKEN = :token", nativeQuery = true
    )
    fun findByToken(token: String): JsonWebToken?
}

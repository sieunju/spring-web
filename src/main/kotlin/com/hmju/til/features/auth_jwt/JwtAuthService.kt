package com.hmju.til.features.auth_jwt

import com.hmju.til.features.auth_jwt.model.dto.JsonWebTokenDTO

/**
 * Description : JWT Auth Service Interface
 *
 * Created by juhongmin on 1/26/24
 */
interface JwtAuthService {
    fun save(dto: JsonWebTokenDTO)

    fun refresh(token: String): JsonWebTokenDTO

    fun delete(id: Long)

    fun delete(dto: JsonWebTokenDTO)
}
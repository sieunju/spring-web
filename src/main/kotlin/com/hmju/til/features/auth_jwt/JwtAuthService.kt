package com.hmju.til.features.auth_jwt

import com.hmju.til.features.auth_jwt.model.dto.JsonWebTokenDTO
import com.hmju.til.features.auth_jwt.model.entity.JsonWebToken

/**
 * Description : JWT Auth Service Interface
 *
 * Created by juhongmin on 1/26/24
 */
interface JwtAuthService {

    fun save(dto: JsonWebTokenDTO)

    fun findAuth(token: String): JsonWebToken?

    fun refresh(token: String) : JsonWebToken

    fun update(dto: JsonWebTokenDTO): JsonWebToken

    fun delete(id: Long)

    fun delete(dto: JsonWebTokenDTO)
}

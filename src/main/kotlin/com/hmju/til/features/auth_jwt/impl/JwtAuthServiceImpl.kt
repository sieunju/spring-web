package com.hmju.til.features.auth_jwt.impl

import com.hmju.til.component.JwtComponent
import com.hmju.til.core.exception.InvalidJwtException
import com.hmju.til.features.auth_jwt.JwtAuthRepository
import com.hmju.til.features.auth_jwt.JwtAuthService
import com.hmju.til.features.auth_jwt.model.dto.JsonWebTokenDTO
import com.hmju.til.features.auth_jwt.model.entity.JsonWebToken
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * Description : JWT Auth Service Impl Class
 *
 * Created by juhongmin on 1/27/24
 */
@Service
@Suppress("unused")
internal class JwtAuthServiceImpl @Autowired constructor(
    private val repository: JwtAuthRepository,
    private val jwtComponent: JwtComponent
) : JwtAuthService {

    private val logger: Logger by lazy { LoggerFactory.getLogger(this.javaClass) }

    override fun save(dto: JsonWebTokenDTO) {
        repository.saveAndFlush(JsonWebToken(dto))
    }

    override fun findAuth(token: String): JsonWebToken? {
        return repository.findByToken(token)
    }

    /**
     * RefreshToken 을 가져와서 토큰을 새로 교체해서 리턴하는 함수
     * 유효성 검사 후 새로 발급해서 전달한다.
     * @param token Refresh Token
     */
    @Throws(InvalidJwtException::class)
    override fun refresh(token: String): JsonWebToken {
        throw InvalidJwtException("")
    }

    override fun update(dto: JsonWebTokenDTO): JsonWebToken {
        return repository.save(JsonWebToken(dto))
    }

    override fun delete(id: Long) {
        repository.deleteById(id)
    }

    override fun delete(dto: JsonWebTokenDTO) {
        repository.delete(JsonWebToken(dto))
    }
}

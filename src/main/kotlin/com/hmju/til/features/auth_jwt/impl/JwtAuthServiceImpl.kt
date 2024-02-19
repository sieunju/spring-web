package com.hmju.til.features.auth_jwt.impl

import com.hmju.til.component.JwtComponent
import com.hmju.til.core.exception.InvalidAuthException
import com.hmju.til.features.auth_jwt.JwtAuthRepository
import com.hmju.til.features.auth_jwt.JwtAuthService
import com.hmju.til.features.auth_jwt.model.dto.JsonWebTokenDTO
import com.hmju.til.features.auth_jwt.model.entity.JsonWebToken
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Description : JWT Auth Service Impl Class
 *
 * Created by juhongmin on 1/27/24
 */
@Service
@Suppress("unused")
internal class JwtAuthServiceImpl
    @Autowired
    constructor(
        private val repository: JwtAuthRepository,
        private val jwtComponent: JwtComponent,
    ) : JwtAuthService {
        private val logger: Logger by lazy { LoggerFactory.getLogger(this.javaClass) }

        override fun save(dto: JsonWebTokenDTO) {
            repository.saveAndFlush(JsonWebToken(dto))
        }

        /**
         * RefreshToken 을 가져와서 토큰을 새로 교체해서 리턴하는 함수
         * 유효성 검사 후 새로 발급해서 전달한다.
         * @param token Refresh Token
         */
        @Transactional(value = "mainTransactionManagerFactory", rollbackFor = [InvalidAuthException::class])
        @Throws(InvalidAuthException::class)
        override fun refresh(token: String): JsonWebTokenDTO {
            val entity = repository.findByToken(token) ?: throw InvalidAuthException(token)
            // 1. 삭제 예정으로 변경
            if (!entity.isDelete) {
                repository.save(entity.copy(isDelete = true))
            }
            // 2. Token 재발급
            val info = jwtComponent.create(entity)
            repository.save(JsonWebToken(info))
            return JsonWebTokenDTO(info)
        }

        override fun delete(id: Long) {
            repository.deleteById(id)
        }

        override fun delete(dto: JsonWebTokenDTO) {
            repository.delete(JsonWebToken(dto))
        }

        @Scheduled(cron = "0 0 0 1/2 * ?", zone = "Asia/Seoul")
        fun expiredToken() {
            val list = repository.findExpiredToken()
            repository.deleteAll(list)
            logger.info("삭제할 토큰들 작업 완료 했습니다.")
        }
    }
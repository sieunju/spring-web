package com.hmju.til.features.auth_jwt

import com.hmju.til.component.JwtComponent
import com.hmju.til.core.model.JSendMeta
import com.hmju.til.core.model.JSendResponse
import com.hmju.til.features.auth_jwt.model.dto.AuthDTO
import com.hmju.til.features.auth_jwt.model.dto.JsonWebTokenDTO
import com.hmju.til.features.auth_jwt.model.vo.AuthVo
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

/**
 * Description : JWT Auth Controller
 *
 * Created by juhongmin on 1/15/24
 */
@Tag(name = "Auth JWT", description = "Auth API")
@RestController
@RequestMapping("/api/v1/auth", produces = [MediaType.APPLICATION_JSON_VALUE])
@Suppress("unused")
class AuthController @Autowired constructor(
    private val jwtComponent: JwtComponent,
    private val service: JwtAuthService
) {

    private val logger: Logger by lazy { LoggerFactory.getLogger(this.javaClass) }
    private val emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$".toRegex()

    @PostMapping("/token")
    fun postToken(
        @RequestBody body: AuthVo
    ): JSendResponse<AuthDTO, JSendMeta> {
        if (body.email.isEmpty() || !emailRegex.matches(body.email)) {
            throw IllegalArgumentException("이메일이 유효하지 않습니다.")
        }
        val info = jwtComponent.create(body)
        // Refresh Token 저장
        service.save(JsonWebTokenDTO(info))
        return JSendResponse.Builder<AuthDTO, JSendMeta>()
            .setPayload(AuthDTO(info))
            .build()
    }

    @SecurityRequirement(name = "JWT Auth")
    @PostMapping("/refresh")
    fun postRefresh(
        @RequestHeader(value = AUTHORIZATION) authorization: String
    ): JSendResponse<AuthDTO, JSendMeta> {
        val dto = service.refresh(jwtComponent.getHeaderToken(authorization))
        return JSendResponse.Builder<AuthDTO, JSendMeta>()
            .setPayload(AuthDTO(dto))
            .build()
    }
}

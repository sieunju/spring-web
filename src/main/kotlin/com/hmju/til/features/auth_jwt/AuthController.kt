package com.hmju.til.features.auth_jwt

import com.hmju.til.component.JwtComponent
import com.hmju.til.core.model.JSendMeta
import com.hmju.til.core.model.JSendResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Description : JWT Auth Controller
 *
 * Created by juhongmin on 1/15/24
 */
@Tag(name = "[Test] Auth JWT", description = "JWT 테스트용 API")
@RestController
@RequestMapping("/api/v1/til/jwt", produces = [MediaType.APPLICATION_JSON_VALUE])
@Suppress("unused")
class AuthController @Autowired constructor(
    private val jwtComponent: JwtComponent
){
    @PostMapping("/token")
    fun postToken() : JSendResponse<String,JSendMeta> {
        val token = jwtComponent.createToken("test@gmail.com").replace("-","+").replace("_","/")
        return JSendResponse.Builder<String,JSendMeta>()
            .setPayload(token)
            .build()
    }
}

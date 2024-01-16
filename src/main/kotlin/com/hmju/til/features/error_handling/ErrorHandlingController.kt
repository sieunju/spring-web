package com.hmju.til.features.error_handling

import com.hmju.til.core.model.JSendMeta
import com.hmju.til.core.model.JSendResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Description : Test Error Handling Controller
 *
 * Created by juhongmin on 1/13/24
 */
@Tag(name = "[Test] Error Handling", description = "테스트용 에러 API")
@RestController
@RequestMapping("/api/v1/til/error", produces = [MediaType.APPLICATION_JSON_VALUE])
@Suppress("unused")
class ErrorHandlingController {
    @GetMapping("/505")
    fun fetch505(): JSendResponse<Any, JSendMeta> {
        return JSendResponse.Builder<Any, JSendMeta>()
            .setStatus(HttpStatus.HTTP_VERSION_NOT_SUPPORTED)
            .setMessage("505 Get 방식의 Error 입니다.")
            .build()
    }

    @PostMapping("/505")
    fun post505(): JSendResponse<Any, JSendMeta> {
        return JSendResponse.Builder<Any, JSendMeta>()
            .setStatus(HttpStatus.HTTP_VERSION_NOT_SUPPORTED)
            .setMessage("505 Post 방식의 Error 입니다.")
            .build()
    }

    @PostMapping("/404")
    fun post404(): JSendResponse<Any, JSendMeta> {
        return JSendResponse.Builder<Any, JSendMeta>()
            .setStatus(HttpStatus.NOT_FOUND)
            .setMessage("404 Post 방식의 Error 입니다.")
            .build()
    }

    @GetMapping("/404")
    fun fetch404(): JSendResponse<Any, JSendMeta> {
        return JSendResponse.Builder<Any, JSendMeta>()
            .setStatus(HttpStatus.NOT_FOUND)
            .setMessage("Hello Error Contents")
            .build()
    }
}

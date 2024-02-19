package com.hmju.til.features.auth_jwt_test

import com.hmju.til.core.model.JSendMeta
import com.hmju.til.core.model.JSendResponse
import com.hmju.til.features.auth_jwt_test.model.JwtPerformanceTestDTO
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.scheduling.annotation.Async
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.CompletableFuture

/**
 * Description : JWT 성능 체크 용 Controller
 *
 * Created by juhongmin on 2/18/24
 */
@SecurityRequirement(name = "JWT Auth")
@RestController
@RequestMapping("/api/v1/til/auth/jwt", produces = [MediaType.APPLICATION_JSON_VALUE])
@Suppress("unused")
class JwtPerformanceTestController @Autowired constructor(
    private val executor: ThreadPoolTaskExecutor
) {

    private val logger: Logger by lazy { LoggerFactory.getLogger(this.javaClass) }

    @Async("controllerExecutor")
    @GetMapping("/test")
    fun fetchTest(
        @RequestParam(name = "time_delay", defaultValue = "0") delay: Int
    ): CompletableFuture<JSendResponse<JwtPerformanceTestDTO, JSendMeta>> {
        Thread.sleep(delay.toLong())
        return CompletableFuture.completedFuture(
            JSendResponse.Builder<JwtPerformanceTestDTO, JSendMeta>()
                .setPayload(JwtPerformanceTestDTO("JWT Token Test ActiveCount", executor.activeCount))
                .build()
        )
    }

    @Async("controllerExecutor")
    @GetMapping("/test1")
    fun fetchTest1(
        @RequestParam(name = "time_delay", defaultValue = "0") delay: Int
    ): CompletableFuture<JSendResponse<JwtPerformanceTestDTO, JSendMeta>> {
        Thread.sleep(delay.toLong())
        return CompletableFuture.completedFuture(
            JSendResponse.Builder<JwtPerformanceTestDTO, JSendMeta>()
                .setPayload(JwtPerformanceTestDTO("JWT Token Test1", executor.activeCount))
                .build()
        )
    }

    @Async("controllerExecutor")
    @GetMapping("/test2")
    fun fetchTest2(
        @RequestParam(name = "time_delay", defaultValue = "0") delay: Int
    ): CompletableFuture<JSendResponse<JwtPerformanceTestDTO, JSendMeta>> {
        Thread.sleep(delay.toLong())
        return CompletableFuture.completedFuture(
            JSendResponse.Builder<JwtPerformanceTestDTO, JSendMeta>()
                .setPayload(JwtPerformanceTestDTO("JWT Token Test2 ActiveCount", executor.activeCount))
                .build()
        )
    }
}

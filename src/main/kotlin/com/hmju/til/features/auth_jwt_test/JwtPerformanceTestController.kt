package com.hmju.til.features.auth_jwt_test

import com.hmju.til.core.model.JSendMeta
import com.hmju.til.core.model.JSendResponse
import com.hmju.til.features.auth_jwt_test.model.JwtPerformanceTestDTO
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import java.time.Duration
import java.util.concurrent.Executors

/**
 * Description : JWT 성능 체크 용 Controller
 *
 * Created by juhongmin on 2/18/24
 */
@SecurityRequirement(name = "JWT Auth")
@RestController
@RequestMapping("/api/v1/til/auth/jwt", produces = [MediaType.APPLICATION_JSON_VALUE])
@Suppress("unused")
class JwtPerformanceTestController {

    private val logger: Logger by lazy { LoggerFactory.getLogger(this.javaClass) }

    @GetMapping("/test")
    fun fetchTest(
        @RequestParam(name = "time_delay", defaultValue = "0") delay: Int,
    ): Mono<JSendResponse<JwtPerformanceTestDTO, JSendMeta>> {
        val startTime = System.currentTimeMillis()
        return Mono.delay(Duration.ofMillis(delay.toLong()))
            .publishOn(Schedulers.fromExecutor(Executors.newCachedThreadPool()))
            .map {
                JSendResponse.Builder<JwtPerformanceTestDTO, JSendMeta>()
                    .setPayload(JwtPerformanceTestDTO(startTime))
                    .build()
            }
    }

    @GetMapping("/test1")
    fun fetchTest1(
        @RequestParam(name = "time_delay", defaultValue = "0") delay: Int,
    ): Mono<JSendResponse<JwtPerformanceTestDTO, JSendMeta>> {
        val startTime = System.currentTimeMillis()
        return Mono.delay(Duration.ofMillis(delay.toLong()))
            .publishOn(Schedulers.fromExecutor(Executors.newCachedThreadPool()))
            .map {
                JSendResponse.Builder<JwtPerformanceTestDTO, JSendMeta>()
                    .setPayload(JwtPerformanceTestDTO(startTime))
                    .build()
            }
    }

    @GetMapping("/test2")
    fun fetchTest2(
        @RequestParam(name = "time_delay", defaultValue = "0") delay: Int,
    ): Mono<JSendResponse<JwtPerformanceTestDTO, JSendMeta>> {
        val startTime = System.currentTimeMillis()
        return Mono.delay(Duration.ofMillis(delay.toLong()))
            .publishOn(Schedulers.fromExecutor(Executors.newCachedThreadPool()))
            .map {
                JSendResponse.Builder<JwtPerformanceTestDTO, JSendMeta>()
                    .setPayload(JwtPerformanceTestDTO(startTime))
                    .build()
            }
    }

    @GetMapping("/test3")
    fun fetchTest3(
        @RequestParam(name = "time_delay", defaultValue = "0") delay: Int
    ): Mono<JSendResponse<JwtPerformanceTestDTO, JSendMeta>> {
        val startTime = System.currentTimeMillis()
        return Mono.delay(Duration.ofMillis(delay.toLong()))
            .publishOn(Schedulers.fromExecutor(Executors.newCachedThreadPool()))
            .map {
                JSendResponse.Builder<JwtPerformanceTestDTO, JSendMeta>()
                    .setPayload(JwtPerformanceTestDTO(startTime))
                    .build()
            }
    }
}
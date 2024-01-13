package com.hmju.til.jsend

import com.hmju.til.core.model.JSendMeta
import com.hmju.til.core.model.JSendResponse
import com.hmju.til.jsend.model.TestMeta
import com.hmju.til.jsend.model.TestNoticeMeta
import io.swagger.v3.oas.annotations.tags.Tag
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime
import kotlin.random.Random

/**
 * Description : JSend Format 예시 Controller
 *
 * Created by juhongmin on 1/13/24
 */
@Tag(name = "JSend", description = "JSend Format 테스트용 API")
@RestController
@RequestMapping("/api/v1/til/jsend")
@Suppress("unused")
class JSendController {

    private val logger: Logger by lazy { LoggerFactory.getLogger(this.javaClass) }

    @GetMapping
    fun fetch(): JSendResponse<Any, JSendMeta> {
        return JSendResponse.Builder<Any, JSendMeta>()
            .setPayload(mapOf("id" to LocalDateTime.now()))
            .build()
    }

    @GetMapping("/meta")
    fun fetchMeta(): JSendResponse<Any, JSendMeta> {
        return JSendResponse.Builder<Any, JSendMeta>()
            .setPayload(mapOf("id" to LocalDateTime.now()))
            .setMeta(TestMeta())
            .build()
    }

    @GetMapping("/list")
    fun fetchList(): JSendResponse<Any, JSendMeta> {
        return JSendResponse.Builder<Any, JSendMeta>()
            .setPayload(listOf("aaa", "bbb"))
            .build()
    }

    @GetMapping("/list/meta")
    fun fetchListMeta(): JSendResponse<Any, JSendMeta> {
        val status = if (Random.nextInt(100) > 50) HttpStatus.OK else HttpStatus.NOT_FOUND
        return JSendResponse.Builder<Any, JSendMeta>()
            .setStatus(status)
            .setPayload(listOf("aaa", "bbb"))
            .setMeta(TestMeta())
            .build()
    }

    @GetMapping("/error/test")
    fun fetchErrorTest(): JSendResponse<Any, JSendMeta> {
        val builder = JSendResponse.Builder<Any, JSendMeta>()
        val ran = Random.nextInt(100)
        if (ran < 30) {
            builder.setMessage("Data is Null")
        } else if (ran < 70) {
            builder.setStatus(HttpStatus.NOT_FOUND)
                .setMessage("에러 핸들링 테스트 입니다.")
        } else {
            builder.setStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .setMeta(TestNoticeMeta("서버가 꺼져 있습니다."))
        }
        return builder.build()
    }
}

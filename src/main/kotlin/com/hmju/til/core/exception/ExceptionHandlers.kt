package com.hmju.til.core.exception

import com.hmju.til.core.model.JSendMeta
import com.hmju.til.core.model.JSendResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

/**
 * Description : 공통 에러 핸들러 클래스
 * 아직 에러에 대한 처리가 미비함
 * Created by juhongmin on 12/23/23
 */
@ControllerAdvice
class ExceptionHandlers {

    private val logger: Logger by lazy { LoggerFactory.getLogger(this.javaClass) }

    @ExceptionHandler(JSendException::class)
    fun handleJSendException(
        ex: JSendException
    ): JSendResponse<Any, JSendMeta> {
        logger.info("JSendException $ex")
        return JSendResponse.Builder<Any, JSendMeta>()
            .setStatus(HttpStatus.BAD_REQUEST)
            .setMessage(ex.msg)
            .build()
    }

    @ExceptionHandler(Exception::class)
    fun handleException(
        ex: Exception
    ): JSendResponse<Any, JSendMeta> {
        logger.info("Exception $ex")
        return JSendResponse.Builder<Any, JSendMeta>()
            .setStatus(HttpStatus.INTERNAL_SERVER_ERROR)
            .setMessage(ex.message ?: "")
            .build()
    }
}

package com.hmju.til.core.exception

import com.hmju.til.core.model.JSendMeta
import com.hmju.til.core.model.JSendResponse
import org.springframework.http.HttpStatus
import org.springframework.orm.jpa.JpaSystemException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

/**
 * Description : 공통 에러 핸들러 클래스
 * 아직 에러에 대한 처리가 미비함
 * Created by juhongmin on 12/23/23
 */
@ControllerAdvice
@Suppress("unused")
class ExceptionHandlers {

    @ExceptionHandler(JSendException::class)
    fun handleJSendException(
        ex: JSendException
    ): JSendResponse<Any, JSendMeta> {
        return JSendResponse.Builder<Any, JSendMeta>()
            .setStatus(HttpStatus.BAD_REQUEST)
            .setMessage(ex.msg)
            .build()
    }

    @ExceptionHandler(JpaSystemException::class)
    fun handleJpaSystemException(
        ex: JpaSystemException
    ): JSendResponse<Any, JSendMeta> {
        return JSendResponse.Builder<Any, JSendMeta>()
            .setStatus(HttpStatus.BAD_REQUEST)
            .setMessage("올바르지 않은 데이터 입니다.")
            .build()
    }

    @ExceptionHandler(Exception::class)
    fun handleException(
        ex: Exception
    ): JSendResponse<Any, JSendMeta> {
        return JSendResponse.Builder<Any, JSendMeta>()
            .setStatus(HttpStatus.INTERNAL_SERVER_ERROR)
            .setMessage(ex.message ?: "")
            .build()
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(
        ex: IllegalArgumentException
    ): JSendResponse<Any, JSendMeta> {
        return JSendResponse.Builder<Any, JSendMeta>()
            .setStatus(HttpStatus.BAD_REQUEST)
            .setMessage(ex.message ?: "")
            .build()
    }

    @ExceptionHandler(InvalidAuthException::class)
    fun handleJwtException(
        ex: InvalidAuthException
    ): JSendResponse<Any, JSendMeta> {
        return JSendResponse.Builder<Any, JSendMeta>()
            .setStatus(HttpStatus.INTERNAL_SERVER_ERROR)
            .setMessage(ex.message ?: "")
            .build()
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleNotValidException(
        ex: MethodArgumentNotValidException
    ): JSendResponse<Any, JSendMeta> {
        return JSendResponse.Builder<Any, JSendMeta>()
            .setStatus(HttpStatus.BAD_REQUEST)
            .setMessage("유효한 값이 없습니다.")
            .build()
    }
}

package com.hmju.til.core.exception

import com.fasterxml.jackson.databind.ObjectMapper
import com.hmju.til.core.model.JSendMeta
import com.hmju.til.core.model.JSendResponse
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus

/**
 * Description : 잘못된 인증 Exception
 *
 * Created by juhongmin on 1/19/24
 */
data class InvalidAuthException(
    val auth: String,
) : RuntimeException("유효하지 않는 토큰입니다.") {
    /**
     * 에러 메시지 보내는 함수
     * @param res Send Response
     */
    fun sendErrorBody(res: HttpServletResponse) {
        res.status = HttpStatus.BAD_REQUEST.value()
        res.contentType = "application/json"
        val mapper = ObjectMapper()
        val errorJson =
            mapper.writeValueAsString(
                JSendResponse.Builder<Any, JSendMeta>()
                    .setPayload(message ?: "").getBody(),
            )
        res.writer.write(errorJson)
    }
}
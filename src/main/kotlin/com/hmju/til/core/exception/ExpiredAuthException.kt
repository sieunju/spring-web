package com.hmju.til.core.exception

import com.fasterxml.jackson.databind.ObjectMapper
import com.hmju.til.core.model.JSendMeta
import com.hmju.til.core.model.JSendResponse
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus

/**
 * Description : 만료된 토큰이 온 경우 Exception
 *
 * Created by juhongmin on 1/28/24
 */
data class ExpiredAuthException(
    val auth: String,
) : RuntimeException("만료된 토큰입니다.") {
    /**
     * 에러 메시지 보내는 함수
     * @param res Send Response
     */
    fun sendErrorBody(res: HttpServletResponse) {
        res.status = HttpStatus.UNAUTHORIZED.value()
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
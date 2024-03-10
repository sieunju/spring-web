package com.hmju.til.core.exception

/**
 * Description : API 통신시 에러 발생에 데한 Exception
 *
 * Created by juhongmin on 12/23/23
 */
data class JSendException(
    val code: Int,
    val msg: String
) : RuntimeException(msg)
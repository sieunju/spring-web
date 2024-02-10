package com.hmju.til

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

/**
 * Description : Util 확장 함수
 *
 * Created by juhongmin on 1/27/24
 */
fun Date.toLocalDateTime(): LocalDateTime {
    return Instant.ofEpochMilli(time).atZone(ZoneId.of("Asia/Seoul")).toLocalDateTime()
}

fun LocalDateTime.toDate(): Date {
    return Date.from(atZone(ZoneId.of("Asia/Seoul")).toInstant())
}

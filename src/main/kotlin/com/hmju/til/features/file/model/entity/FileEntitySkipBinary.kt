package com.hmju.til.features.file.model.entity

import java.time.LocalDateTime

/**
 * Description : OBJ 가 빠진 상태로 파일 조회 하기 위한 클래스
 *
 * Created by juhongmin on 5/8/24
 */
interface FileEntitySkipBinary {
    val id: Long
    val org_name: String?
    val path: String
    val mime_type: String?
    val reg_date: LocalDateTime?
}

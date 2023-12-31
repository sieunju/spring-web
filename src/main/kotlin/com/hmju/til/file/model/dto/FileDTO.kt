package com.hmju.til.file.model.dto

import com.hmju.til.file.model.entity.FileEntity
import java.time.LocalDateTime

/**
 * Description : 파일 DTO
 *
 * Created by juhongmin on 12/31/23
 */
data class FileDTO(
    val id: Int? = null,
    val originalName: String? = null,
    val path: String,
    val mimeType: String? = null,
    val registerDate: LocalDateTime? = null
) {
    constructor(
        entity: FileEntity
    ) : this(
        id = entity.id,
        originalName = entity.originalName,
        path = entity.path,
        mimeType = entity.mimeType,
        registerDate = entity.registerDate
    )
}

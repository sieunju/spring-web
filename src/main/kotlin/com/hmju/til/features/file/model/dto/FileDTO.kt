package com.hmju.til.features.file.model.dto

import com.hmju.til.features.file.model.entity.FileEntity
import java.time.LocalDateTime

/**
 * Description : 파일 DTO
 *
 * Created by juhongmin on 12/31/23
 */
data class FileDTO(
    val id: Long? = null,
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
        registerDate = entity.registerDate,
    )

    constructor(
        host: String,
        entity: FileEntity
    ) : this(
        id = entity.id,
        originalName = entity.originalName,
        path = host.plus(entity.path),
        mimeType = entity.mimeType,
        registerDate = entity.registerDate,
    )
}
package com.hmju.til.features.ddm.model.dto

import com.hmju.til.features.ddm.model.entity.DdmEntity
import java.time.LocalDateTime

/**
 * Description : DDM DTO
 *
 * Created by juhongmin on 08/28/24
 */
data class DdmDTO(
    val id: Long? = null,
    val name: String,
    val type: String,
    val version: String,
    val path: String,
    val registerDate: LocalDateTime? = null
) {

    constructor(
        entity: DdmEntity
    ) : this(
        id = entity.id,
        name = entity.name,
        type = entity.type,
        version = entity.version,
        path = entity.path,
        registerDate = entity.registerDate
    )

    constructor(
        host: String,
        entity: DdmEntity
    ) : this(
        id = entity.id,
        name = entity.name,
        type = entity.type,
        version = entity.version,
        path = host.plus(entity.path),
        registerDate = entity.registerDate
    )
}

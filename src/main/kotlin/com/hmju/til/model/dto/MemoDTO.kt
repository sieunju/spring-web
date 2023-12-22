package com.hmju.til.model.dto

import com.hmju.til.model.entity.MemoEntity

/**
 * Description : 메몰 DTO
 *
 * Created by juhongmin on 12/22/23
 */
data class MemoDTO(
    val id: Int,
    val tag: Int,
    val title: String,
    val contents: String
) {
    constructor(
        entity: MemoEntity
    ) : this(
        id = entity.id,
        tag = entity.tag ?: 0,
        title = entity.title,
        contents = entity.contents
    )
}
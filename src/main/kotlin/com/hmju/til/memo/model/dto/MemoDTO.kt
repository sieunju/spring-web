package com.hmju.til.memo.model.dto

import com.hmju.til.memo.model.entity.Memo

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
        entity: Memo
    ) : this(
        id = entity.id,
        tag = entity.tag ?: 0,
        title = entity.title,
        contents = entity.contents
    )
}
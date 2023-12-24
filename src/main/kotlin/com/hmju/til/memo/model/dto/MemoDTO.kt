package com.hmju.til.memo.model.dto

import com.hmju.til.memo.model.entity.Memo
import com.hmju.til.memo.model.vo.MemoVo
import java.time.LocalDateTime

/**
 * Description : 메몰 DTO
 *
 * Created by juhongmin on 12/22/23
 */
data class MemoDTO(
    val id: Int? = null,
    val userId: String? = null,
    val tag: Int? = null,
    val title: String,
    val contents: String,
    val registerDate: LocalDateTime? = null
) {
    constructor(
        entity: Memo
    ) : this(
        id = entity.id,
        userId = entity.userId,
        tag = entity.tag,
        title = entity.title,
        contents = entity.contents,
        registerDate = entity.registerDate
    )

    constructor(
        entity: MemoVo
    ) : this(
        id = entity.id ?: 0,
        userId = entity.userId,
        tag = entity.tag,
        title = entity.title,
        contents = entity.contents,
        registerDate = LocalDateTime.now()
    )
}
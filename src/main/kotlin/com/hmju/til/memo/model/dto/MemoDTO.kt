package com.hmju.til.memo.model.dto

import com.hmju.til.memo.model.entity.Memo
import com.hmju.til.memo.model.vo.MemoVo
import java.time.LocalDate

/**
 * Description : 메몰 DTO
 *
 * Created by juhongmin on 12/22/23
 */
data class MemoDTO(
    val id: Int,
    val tag: Int?,
    val title: String,
    val contents: String,
    val registerDate: LocalDate? = null
) {
    constructor(
        entity: Memo
    ) : this(
        id = entity.id,
        tag = entity.tag,
        title = entity.title,
        contents = entity.contents,
        registerDate = entity.registerDate
    )

    constructor(
        entity: MemoVo
    ) : this(
        id = 0,
        tag = entity.tag,
        title = entity.title,
        contents = entity.contents,
        registerDate = LocalDate.now()
    )
}
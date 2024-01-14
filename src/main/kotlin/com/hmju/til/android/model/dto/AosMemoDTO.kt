package com.hmju.til.android.model.dto

import com.hmju.til.android.model.entity.AosMemo
import com.hmju.til.android.model.vo.AosMemoVO
import java.time.LocalDateTime

/**
 * Description : Android Memo DTO
 *
 * Created by juhongmin on 1/15/24
 */
data class AosMemoDTO(
    val id: Long? = null,
    val title: String,
    val contents: String,
    val registerDate: LocalDateTime? = null
) {
    constructor(
        entity: AosMemo
    ) : this(
        id = entity.id,
        title = entity.title,
        contents = entity.contents,
        registerDate = entity.registerDate
    )

    constructor(
        vo: AosMemoVO
    ) : this(
        id = vo.id ?: 0,
        title = vo.title,
        contents = vo.contents,
        registerDate = LocalDateTime.now()
    )
}

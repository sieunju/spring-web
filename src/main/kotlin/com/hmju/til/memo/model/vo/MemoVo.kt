package com.hmju.til.memo.model.vo

/**
 * Description : 클라이언트에서 들어오는 데이터
 *
 * Created by juhongmin on 12/24/23
 */
data class MemoVo(
    val userId: String? = null,
    val tag: Int? = null,
    val title: String = "",
    val contents: String = ""
)

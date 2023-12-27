package com.hmju.til.memo.model.vo

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Description : Memo VO List
 *
 * Created by juhongmin on 12/24/23
 */
data class MemoListVo(
    @JsonProperty("memo_info_list")
    val list: List<MemoVo> = listOf()
)

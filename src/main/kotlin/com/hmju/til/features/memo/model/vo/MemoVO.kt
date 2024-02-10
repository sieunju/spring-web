package com.hmju.til.features.memo.model.vo

/**
 * Description : 클라이언트에서 들어오는 메모 데이터 모델
 *
 * @param id 메모 아이디 (Nullable)
 * @param userId 추가하는 메모 아이디 (Nullable)
 * @param tag 메모 테그
 * @param title 제목
 * @param contents 내용
 *
 * Created by juhongmin on 12/24/23
 */
data class MemoVO(
    val id: Long? = null,
    val userId: String? = null,
    val tag: Int? = null,
    val title: String = "",
    val contents: String = ""
)

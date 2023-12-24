package com.hmju.til.memo

import com.hmju.til.core.model.PaginationMeta
import com.hmju.til.memo.model.dto.MemoDTO
import com.hmju.til.memo.model.entity.Memo

/**
 * Description : Memo Service Class
 *
 * Created by juhongmin on 12/23/23
 */
interface MemoService {
    /**
     * 메모 데이터 조회
     * @param pageNo 페이지 번호 (1부터 시작)
     * @param pageSize 페이지 사이즈
     */
    fun fetch(
        pageNo: Int,
        pageSize: Int
    ): List<Memo>

    /**
     * 메모 메타 조회
     * @param pageNo 페이지 번호 (1부터 시작)
     * @param pageSize 페이지 사이즈
     */
    fun fetchMeta(
        pageNo: Int,
        pageSize: Int
    ): PaginationMeta

    /**
     * 메모 추가
     * @param body 추가할 메모 데이터 모델
     */
    fun post(body: MemoDTO): Memo
}

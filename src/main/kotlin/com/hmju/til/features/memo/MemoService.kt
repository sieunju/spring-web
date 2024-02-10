package com.hmju.til.features.memo

import com.hmju.til.core.model.PaginationMeta
import com.hmju.til.features.memo.model.dto.MemoDTO
import com.hmju.til.features.memo.model.entity.Memo

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
     * 메모 여러개 추가 bulk 형식
     * @param list 추가할 메도 데이터 여러개
     */
    fun postAll(list: List<MemoDTO>): List<Memo>

    /**
     * 메모 여러개 삭제 하는 함수
     * @param list 삭제할 메모 ID 리스트
     */
    fun deleteAll(list: List<Long>): List<Memo>

    /**
     * 메모 여러개 업데이트 하는 함수
     * @param list 업데이트 할 데이터
     */
    fun updateAll(list: List<MemoDTO>): List<Memo>
}

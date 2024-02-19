package com.hmju.til.features.goods

import com.hmju.til.core.model.PaginationMeta
import com.hmju.til.features.goods.model.dto.GoodsDTO
import com.hmju.til.features.goods.model.entity.Goods

/**
 * Description : Goods Service Interface
 *
 * Created by juhongmin on 1/14/24
 */
interface GoodsService {
    /**
     * 데이터 조회
     * @param pageNo 페이지 번호 (1부터 시작)
     * @param pageSize 페이지 사이즈
     */
    fun fetch(
        pageNo: Int,
        pageSize: Int,
    ): List<Goods>

    /**
     * 메타 조회
     * @param pageNo 페이지 번호 (1부터 시작)
     * @param pageSize 페이지 사이즈
     */
    fun fetchMeta(
        pageNo: Int,
        pageSize: Int,
    ): PaginationMeta

    /**
     * 여러개 추가 bulk 형식
     * @param list 추가할 데이터
     */
    fun postAll(list: List<GoodsDTO>): List<Goods>

    /**
     * 여러개 삭제 하는 함수
     * @param list 삭제할 ID 리스트
     */
    fun deleteAll(list: List<Long>): List<Goods>

    /**
     * 여러개 업데이트 하는 함수
     * @param list 업데이트 할 데이터
     */
    fun updateAll(list: List<GoodsDTO>): List<Goods>
}
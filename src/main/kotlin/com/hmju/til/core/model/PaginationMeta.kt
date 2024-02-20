package com.hmju.til.core.model

/**
 * Description : 페이징 네이션 메타 공통 데이터 모델
 *
 * @param totalCount 총 개수
 * @param nextPage 다음 페이지가 없으면 Null
 * @param currentPage 현재 페이지
 *
 * Created by juhongmin on 12/22/23
 */
open class PaginationMeta(
    open val totalCount: Int = 0,
    open val nextPage: Int? = null,
    open val currentPage: Int = 1,
) : JSendMeta()
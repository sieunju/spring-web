package com.hmju.til.service

import com.hmju.til.model.base.PaginationMeta
import com.hmju.til.model.entity.MemoEntity
import com.hmju.til.repository.MemoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * Description :
 *
 * Created by juhongmin on 12/22/23
 */

@Service
class MemoServiceImpl @Autowired constructor(
    private val repository: MemoRepository
) {

    /**
     * 메모 데이터 조회
     * @param pageNo 페이지 번호 (1부터 시작)
     * @param pageSize 페이지 사이즈
     */
    fun fetch(
        pageNo: Int,
        pageSize: Int
    ): List<MemoEntity> {
        // Start Index 계산
        var offset = Math.max(pageNo.minus(1), 0)
        offset *= pageSize
        return repository.findRange(offset, pageSize)
    }

    /**
     * 메모 메타 조회
     * @param pageNo 페이지 번호 (1부터 시작)
     * @param pageSize 페이지 사이즈
     */
    fun fetchMeta(
        pageNo: Int,
        pageSize: Int
    ): PaginationMeta {
        val count = repository.count()
        val no = Math.max(1, pageNo) // pageNo 0 으로 줄때 대응
        var maxPage = count / pageSize
        // 나머지가 있는 경우 1추가
        if (count % pageSize > 0) {
            maxPage++
        }
        val nextPage = if (maxPage > no) {
            no.plus(1)
        } else {
            null
        }
        return PaginationMeta(
            totalCount = count.toInt(),
            nextPage = nextPage,
            currentPage = pageNo
        )
    }
}

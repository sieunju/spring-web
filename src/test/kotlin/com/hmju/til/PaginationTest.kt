package com.hmju.til

import com.hmju.til.core.model.PaginationMeta
import org.junit.jupiter.api.Test

/**
 * Description :
 *
 * Created by juhongmin on 12/23/23
 */
class PaginationTest {

    @Test
    fun 페이지네이션_OFFSET_테스트() {
        // offset = 0, limit = 10
        // limit 10 일떄
        // 1 -> 0, 2 -> 10, 3-> 20
        // 1 -> 0
        assert(getOffset(1, 26) == 0)
        assert(getOffset(2, 26) == 26)
        assert(getOffset(3, 26) == 52)
    }

    private fun getOffset(pageNo: Int, pageSize: Int): Int {
        val offset = Math.max(pageNo.minus(1), 0)
        return offset * pageSize
    }

    @Test
    fun 페이지네이션_메타_테스트() {
        assert(getPaginationMeta(1, 30, 31).nextPage == 2)
        assert(getPaginationMeta(2,30,31).nextPage == null)
        assert(getPaginationMeta(1,30,30).nextPage == null)
    }

    private fun getPaginationMeta(
        pageNo: Int, // 최소값 1부터 시작
        pageSize: Int,
        count: Int
    ): PaginationMeta {
        val no = Math.max(1, pageNo)
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
            totalCount = count,
            nextPage = nextPage,
            currentPage = pageNo
        )
    }
}

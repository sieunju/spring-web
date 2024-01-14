package com.hmju.til.goods.impl

import com.hmju.til.core.model.PaginationMeta
import com.hmju.til.goods.GoodsRepository
import com.hmju.til.goods.GoodsService
import com.hmju.til.goods.model.dto.GoodsDTO
import com.hmju.til.goods.model.entity.Goods
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

/**
 * Description : Goods Service Impl Class
 *
 * Created by juhongmin on 1/14/24
 */
@Service
@Suppress("unused")
class GoodsServiceImpl @Autowired constructor(
    private val repository: GoodsRepository
) : GoodsService {

    /**
     * 데이터 조회
     * @param pageNo 페이지 번호 (1부터 시작)
     * @param pageSize 페이지 사이즈
     */
    override fun fetch(
        pageNo: Int,
        pageSize: Int
    ): List<Goods> {
        val pageable = PageRequest.of(
            pageNo.minus(1).coerceAtLeast(0),
            pageSize,
            Sort.by("id").ascending()
        )
        return repository.findAll(pageable).content
    }

    /**
     * 메타 조회
     * @param pageNo 페이지 번호 (1부터 시작)
     * @param pageSize 페이지 사이즈
     */
    override fun fetchMeta(pageNo: Int, pageSize: Int): PaginationMeta {
        val count = repository.count()
        val no = 1.coerceAtLeast(pageNo) // pageNo 0 으로 줄때 대응
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

    /**
     * 여러개 추가 bulk 형식
     * @param list 추가할 데이터
     */
    override fun postAll(list: List<GoodsDTO>): List<Goods> {
        return repository.saveAll(list.map { Goods(it) })
    }

    /**
     * 여러개 삭제 하는 함수
     * @param list 삭제할 ID 리스트
     */
    override fun deleteAll(list: List<Long>): List<Goods> {
        val selectList = repository.findAllById(list)
        repository.deleteAllById(selectList.map { it.id })
        return selectList
    }

    /**
     * 여러개 업데이트 하는 함수
     * @param list 업데이트 할 데이터
     */
    override fun updateAll(list: List<GoodsDTO>): List<Goods> {
        val filterList = list.filter { it.id != null && it.id > 0L }
        return repository.saveAll(filterList.map { Goods(it) })
    }
}

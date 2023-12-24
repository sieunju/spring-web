package com.hmju.til.memo

import com.hmju.til.core.model.JSendMeta
import com.hmju.til.core.model.JSendResponse
import com.hmju.til.core.model.PaginationMeta
import com.hmju.til.memo.model.dto.MemoDTO
import com.hmju.til.memo.model.vo.MemoVo
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

/**
 * Description : Memo Controller
 *
 * Created by juhongmin on 12/22/23
 */
@RestController
@RequestMapping("/api/v1/memo")
@Suppress("unused")
class MemoController @Autowired constructor(
    private val service: MemoService
) {

    private val logger: Logger by lazy { LoggerFactory.getLogger(this.javaClass) }

    /**
     * 메모 조회
     * @param pageNo 페이지 번호
     * @param pageSize 페이지 사이즈
     */
    @GetMapping
    fun fetch(
        @RequestParam(name = "pageNo", defaultValue = "1") pageNo: Int,
        @RequestParam(name = "pageSize", defaultValue = "30") pageSize: Int
    ): JSendResponse<MemoDTO, PaginationMeta> {
        return JSendResponse.Builder<MemoDTO, PaginationMeta>()
            .setMeta(service.fetchMeta(pageNo, pageSize))
            .setPayload(service.fetch(pageNo, pageSize).map { MemoDTO(it) })
            .build()
    }

    /**
     * 메모 추가
     * @param body 추가할 메모 데이터
     */
    @PostMapping
    fun post(
        @RequestBody body: MemoVo
    ): JSendResponse<MemoDTO, JSendMeta> {
        val memo = service.post(MemoDTO(body))
        return JSendResponse.Builder<MemoDTO, JSendMeta>()
            .setPayload(MemoDTO(memo))
            .build()
    }
}
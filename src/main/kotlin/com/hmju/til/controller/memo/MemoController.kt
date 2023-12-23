package com.hmju.til.controller.memo

import com.hmju.til.model.base.JSendResponse
import com.hmju.til.model.base.PaginationMeta
import com.hmju.til.model.dto.MemoDTO
import com.hmju.til.service.MemoServiceImpl
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * Description : Memo Controller
 *
 * Created by juhongmin on 12/22/23
 */
@RestController
@RequestMapping("/api/v1/memo")
@Suppress("unused")
class MemoController @Autowired constructor(
    private val service: MemoServiceImpl
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
}
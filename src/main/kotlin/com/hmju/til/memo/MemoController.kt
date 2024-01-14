package com.hmju.til.memo

import com.hmju.til.core.model.JSendMeta
import com.hmju.til.core.model.JSendResponse
import com.hmju.til.core.model.PaginationMeta
import com.hmju.til.memo.model.dto.MemoDTO
import com.hmju.til.memo.model.vo.MemoVO
import io.swagger.v3.oas.annotations.tags.Tag
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

/**
 * Description : Memo Controller
 *
 * Created by juhongmin on 12/22/23
 */
@Tag(name = "Memo", description = "메모 API")
@RestController
@RequestMapping("/api/v1/memo", produces = [MediaType.APPLICATION_JSON_VALUE])
@Suppress("unused")
class MemoController @Autowired constructor(
    private val service: MemoService
) {

    private val logger: Logger by lazy { LoggerFactory.getLogger(this.javaClass) }

    /**
     * 메모 추가 bulk 형식
     * @param list 추가할 메모 데이터
     */
    @PostMapping
    fun post(
        @RequestBody list: List<MemoVO>
    ): JSendResponse<MemoDTO, JSendMeta> {
        return JSendResponse.Builder<MemoDTO, JSendMeta>()
            .setPayload(service.postAll(list.map { MemoDTO(it) }).map { MemoDTO(it) })
            .build()
    }

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
     * 메모 데이터 업데이트
     * @param list 업데이트할 메모 데이터
     */
    @PutMapping
    fun update(
        @RequestBody list: List<MemoVO>
    ): JSendResponse<MemoDTO, JSendMeta> {
        return JSendResponse.Builder<MemoDTO, JSendMeta>()
            .setPayload(service.updateAll(list.map { MemoDTO(it) }).map { MemoDTO(it) })
            .build()
    }

    /**
     * 메모 삭제 bulk 형식
     * @param ids 삭제할 메모 아이디들
     */
    @DeleteMapping
    fun delete(
        @RequestParam(name = "ids") ids: List<Long>
    ): JSendResponse<MemoDTO, JSendMeta> {
        return JSendResponse.Builder<MemoDTO, JSendMeta>()
            .setPayload(service.deleteAll(ids).map { MemoDTO(it) })
            .setMessage("성공적으로 삭제 완료 했습니다.")
            .build()
    }
}
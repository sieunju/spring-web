package com.hmju.til.goods

import com.hmju.til.core.model.JSendMeta
import com.hmju.til.core.model.JSendResponse
import com.hmju.til.core.model.PaginationMeta
import com.hmju.til.goods.model.dto.GoodsDTO
import com.hmju.til.goods.model.vo.GoodsVO
import io.swagger.v3.oas.annotations.tags.Tag
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

/**
 * Description : Goods Controller
 *
 * Created by juhongmin on 1/13/24
 */
@Tag(name = "[Test] Goods", description = "Goods 테스트용 API")
@RestController
@RequestMapping("/api/v1/til/goods", produces = [MediaType.APPLICATION_JSON_VALUE])
@Suppress("unused")
class GoodsController @Autowired constructor(
    private val service: GoodsService
) {

    private val logger: Logger by lazy { LoggerFactory.getLogger(this.javaClass) }

    /**
     * 상품 저장
     * @param list 추가할 데이터
     */
    @PostMapping
    fun post(
        @RequestBody list: List<GoodsVO>
    ): JSendResponse<GoodsDTO, JSendMeta> {
        return JSendResponse.Builder<GoodsDTO, JSendMeta>()
            .setPayload(service.postAll(list.map { GoodsDTO(it) }).map { GoodsDTO(it) })
            .build()
    }

    /**
     * 데이터 조회
     * @param pageNo 페이지 번호
     * @param pageSize 페이지 개수
     */
    @GetMapping
    fun fetch(
        @RequestParam(name = "pageNo", defaultValue = "1") pageNo: Int,
        @RequestParam(name = "pageSize", defaultValue = "30") pageSize: Int
    ): JSendResponse<GoodsDTO, PaginationMeta> {
        return JSendResponse.Builder<GoodsDTO, PaginationMeta>()
            .setMeta(service.fetchMeta(pageNo, pageSize))
            .setPayload(service.fetch(pageNo, pageSize).map { GoodsDTO(it) })
            .build()
    }

    /**
     * 데이터 업데이트
     * @param list 업데이트할 데이터
     */
    @PutMapping
    fun update(
        @RequestBody list: List<GoodsVO>
    ): JSendResponse<GoodsDTO, JSendMeta> {
        return JSendResponse.Builder<GoodsDTO, JSendMeta>()
            .setPayload(service.updateAll(list.map { GoodsDTO(it) }).map { GoodsDTO(it) })
            .build()
    }

    /**
     * 삭제 bulk 형식
     * @param ids 삭제할 아이디들
     */
    @DeleteMapping
    fun delete(
        @RequestParam(name = "ids") ids: List<Long>
    ): JSendResponse<GoodsDTO, JSendMeta> {
        return JSendResponse.Builder<GoodsDTO, JSendMeta>()
            .setPayload(service.deleteAll(ids).map { GoodsDTO(it) })
            .setMessage("성공적으로 삭제 완료 했습니다.")
            .build()
    }
}

package com.hmju.til.features.android

import com.hmju.til.features.android.model.dto.AosMemoDTO
import com.hmju.til.features.android.model.vo.AosMemoVO
import com.hmju.til.core.model.JSendMeta
import com.hmju.til.core.model.JSendResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Description : Android Memo Controller
 *
 * Created by juhongmin on 1/15/24
 */
@Tag(name = "Aos Memo", description = "Android Memo API")
@RestController
@RequestMapping("/api/v1/memo/aos", produces = [MediaType.APPLICATION_JSON_VALUE])
@Suppress("unused")
class AosMemoController @Autowired constructor(
    private val service: AosMemoService
) {
    @GetMapping
    fun fetch(): JSendResponse<AosMemoDTO, JSendMeta> {
        return JSendResponse.Builder<AosMemoDTO, JSendMeta>()
            .setPayload(service.fetch().map { AosMemoDTO(it) })
            .build()
    }

    @PostMapping
    fun post(
        vo: AosMemoVO
    ): JSendResponse<AosMemoDTO, JSendMeta> {
        return JSendResponse.Builder<AosMemoDTO, JSendMeta>()
            .setPayload(AosMemoDTO(service.post(AosMemoDTO(vo))))
            .build()
    }
}

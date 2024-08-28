package com.hmju.til.features.ddm

import com.hmju.til.core.model.JSendMeta
import com.hmju.til.core.model.JSendResponse
import com.hmju.til.features.ddm.model.dto.DdmDTO
import io.swagger.v3.oas.annotations.tags.Tag
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

/**
 * Description : DDM Controller (바람처럼 왔다가 바람처럼 가는 기능)
 *
 * Created by juhongmin on 08/28/24
 */
@Tag(name = "DDM", description = "바람처럼 왔다가 바람처럼 가는 DDM")
@RestController
@RequestMapping("/api/v1/firmwares", produces = [MediaType.APPLICATION_JSON_VALUE])
@Suppress("unused")
class DdmController @Autowired constructor(
    private val service: DdmService
) {
    private val logger: Logger by lazy { LoggerFactory.getLogger(this.javaClass) }

    @Value("\${remote_host}")
    private lateinit var remoteHost: String

    @GetMapping
    fun fetch(): JSendResponse<DdmDTO, JSendMeta> {
        val list = service.fetch().map { DdmDTO(remoteHost, it) }
        return JSendResponse.Builder<DdmDTO, JSendMeta>()
            .setPayload(service.fetch().map { DdmDTO(remoteHost, it) })
            .build()
    }

    @PostMapping(
        path = ["/upload"],
        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE]
    )
    fun post(
        @RequestPart("files", required = true) file: MultipartFile,
        @RequestPart("type", required = true) type: String,
        @RequestPart("version", required = true) version: String
    ): JSendResponse<DdmDTO, JSendMeta> {
        return JSendResponse.Builder<DdmDTO, JSendMeta>()
            .setPayload(DdmDTO(service.save(type, version, file)))
            .build()
    }
}
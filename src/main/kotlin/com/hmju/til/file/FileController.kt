package com.hmju.til.file

import com.hmju.til.core.model.JSendMeta
import com.hmju.til.core.model.JSendResponse
import com.hmju.til.core.model.PaginationMeta
import com.hmju.til.file.model.dto.FileDTO
import io.swagger.v3.oas.annotations.tags.Tag
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

/**
 * Description : File Controller
 *
 * Created by juhongmin on 12/30/23
 */
@Tag(name = "Uploads", description = "파일 업로드 API")
@RestController
@RequestMapping("/api/v1/uploads", produces = [MediaType.APPLICATION_JSON_VALUE])
@Suppress("unused")
class FileController @Autowired constructor(
    private val service: FileService
) {

    private val logger: Logger by lazy { LoggerFactory.getLogger(this.javaClass) }

    @Value("\${remote_host}")
    private lateinit var remoteHost: String

    @GetMapping
    fun fetch(
        @RequestParam(name = "pageNo", defaultValue = "1") pageNo: Int,
        @RequestParam(name = "pageSize", defaultValue = "30") pageSize: Int
    ): JSendResponse<FileDTO, PaginationMeta> {
        return JSendResponse.Builder<FileDTO, PaginationMeta>()
            .setPayload(service.fetch(pageNo, pageSize).map { FileDTO(remoteHost, it) })
            .setMeta(service.fetchMeta(pageNo, pageSize))
            .build()
    }

    @PostMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun post(
        @RequestPart("files", required = true) files: List<MultipartFile>
    ): JSendResponse<FileDTO, JSendMeta> {
        return JSendResponse.Builder<FileDTO, JSendMeta>()
            .setPayload(service.postAll(files).map { FileDTO(it) })
            .build()
    }
}

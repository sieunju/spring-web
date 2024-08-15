package com.hmju.til.features.file

import com.hmju.til.core.exception.JSendException
import com.hmju.til.core.model.JSendMeta
import com.hmju.til.core.model.JSendResponse
import com.hmju.til.core.model.PaginationMeta
import com.hmju.til.features.file.model.dto.FileCleaningDTO
import com.hmju.til.features.file.model.dto.FileDTO
import com.hmju.til.features.file.model.vo.FileCleaningVO
import io.swagger.v3.oas.annotations.Hidden
import io.swagger.v3.oas.annotations.security.SecurityRequirement
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
@SecurityRequirement(name = "JWT Auth")
@RestController
@RequestMapping("/api/v1/uploads", produces = [MediaType.APPLICATION_JSON_VALUE])
@Suppress("unused")
class FileController @Autowired constructor(
    private val service: FileService
) {
    private val logger: Logger by lazy { LoggerFactory.getLogger(this.javaClass) }

    @Value("\${remote_host}")
    private lateinit var remoteHost: String

    @Value("\${file_cleaning.user}")
    private lateinit var fileCleaningUser: String

    @Value("\${file_cleaning.password}")
    private lateinit var fileCleaningPassword: String

    @GetMapping
    fun fetch(
        @RequestParam(name = "pageNo", defaultValue = "1") pageNo: Int,
        @RequestParam(name = "pageSize", defaultValue = "30") pageSize: Int,
    ): JSendResponse<FileDTO, PaginationMeta> {
        return JSendResponse.Builder<FileDTO, PaginationMeta>()
            .setPayload(service.fetch(pageNo, pageSize).map { FileDTO(remoteHost, it) })
            .setMeta(service.fetchMeta(pageNo, pageSize))
            .build()
    }

    @PostMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun post(
        @RequestPart("files", required = true) files: List<MultipartFile>,
    ): JSendResponse<FileDTO, JSendMeta> {
        return JSendResponse.Builder<FileDTO, JSendMeta>()
            .setPayload(service.postAll(files).map { FileDTO(it) })
            .build()
    }

//    @PostMapping("/cleaning")
//    // @Hidden
//    fun fileCleaning(
//        @RequestBody body: FileCleaningVO
//    ): Mono<JSendResponse<FileCleaningDTO, JSendMeta>> {
//        if (body.user != fileCleaningUser ||
//            body.password != fileCleaningPassword
//        ) throw JSendException(404, "유효하지 않습니다.")
//        return Mono.create { emitter ->
//            val payload = service.cleaning()
//            emitter.success(payload)
//        }.subscribeOn(Schedulers.fromExecutor(Executors.newCachedThreadPool()))
//            .map {
//                JSendResponse.Builder<FileCleaningDTO, JSendMeta>()
//                    .setPayload(it)
//                    .build()
//            }
//    }

    @PostMapping("/cleaning")
    @Hidden
    fun fileCleaning(
        @RequestBody body: FileCleaningVO
    ): JSendResponse<FileCleaningDTO, JSendMeta> {
        if (body.user != fileCleaningUser ||
            body.password != fileCleaningPassword
        ) throw JSendException(404, "유효하지 않습니다.")
        return JSendResponse.Builder<FileCleaningDTO, JSendMeta>()
            .setPayload(service.cleaning())
            .build()
    }
}
package com.hmju.til.features.ddm

import com.hmju.til.features.ddm.model.entity.DdmEntity
import io.swagger.v3.oas.annotations.tags.Tag
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDateTime

/**
 * Description : DDM Controller (바람처럼 왔다가 바람처럼 가는 기능)
 *
 * Created by juhongmin on 08/28/24
 */
@Tag(name = "DDM", description = "바람처럼 왔다가 바람처럼 가는 DDM")
@RestController
@RequestMapping("/firmware", produces = [MediaType.APPLICATION_JSON_VALUE])
@Suppress("unused")
class DdmController @Autowired constructor(
    private val service: DdmService
) {
    private val logger: Logger by lazy { LoggerFactory.getLogger(this.javaClass) }

    @Value("\${remote_host}")
    private lateinit var remoteHost: String

    data class DdmListResponse(
        val status: String = "SUCCESS",
        val list: List<DdmListData>
    )

    /**
     * 바람 처럼 왔다가 가는거라 임시로 만듬
     */
    data class DdmListData(
        val id: Long,
        val name: String,
        val file: String,
        val deviceType: String,
        val uploadAt: LocalDateTime
    ) {
        constructor(
            host: String,
            entity: DdmEntity
        ) : this(
            id = entity.id,
            name = entity.name,
            file = host.plus(entity.path),
            deviceType = entity.type,
            uploadAt = entity.registerDate
        )
    }

    data class DdmUploadResponse(
        val status: String,
        val exception: String? = null
    )

    enum class DdmDeviceType {
        ALL, V2, N2
    }

    @GetMapping("/list/{type}")
    fun fetch(
        @PathVariable("type", required = true) type: DdmDeviceType
    ): ResponseEntity<DdmListResponse> {
        val list = service.fetch()
            .filter {
                if (type.name == "ALL") {
                    true
                } else {
                    type.name == it.type
                }
            }.map { DdmListData(remoteHost, it) }
        return ResponseEntity<DdmListResponse>(
            DdmListResponse(list = list),
            HttpStatus.OK
        )
    }

    @PostMapping(
        path = ["/upload"],
        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE]
    )
    fun post(
        @RequestPart("file") file: MultipartFile,
        @RequestPart("device_type") type: String,
        @RequestPart("name") name: String
    ): ResponseEntity<DdmUploadResponse> {
        val res = try {
            service.save(deviceType = type, name = name, file = file)
            DdmUploadResponse(status = "SUCCESS")
        } catch (ex: Exception) {
            DdmUploadResponse(status = "FAIL", exception = ex.message)
        }
        return ResponseEntity(res, HttpStatus.OK)
    }
}
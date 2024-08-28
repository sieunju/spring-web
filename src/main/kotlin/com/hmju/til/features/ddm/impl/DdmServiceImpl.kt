package com.hmju.til.features.ddm.impl

import com.hmju.til.features.ddm.DdmRepository
import com.hmju.til.features.ddm.DdmService
import com.hmju.til.features.ddm.model.entity.DdmEntity
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.FileNotFoundException
import java.nio.file.Files
import java.nio.file.Paths
import java.time.LocalDateTime

@Service
@Suppress("unused")
class DdmServiceImpl @Autowired constructor(
    private val repository: DdmRepository
) : DdmService {

    private val logger: Logger by lazy { LoggerFactory.getLogger(this.javaClass) }

    companion object {
        const val DIRECTORY = "./src/main/resources/files"
    }

    override fun fetch(): List<DdmEntity> {
        return repository.findAll(Sort.by("registerDate").descending())
    }

    override fun save(
        deviceType: String,
        version: String,
        file: MultipartFile
    ): DdmEntity {
        createBasePath()
        if (file.isEmpty) throw FileNotFoundException("파일이 없습니다.")
        val currentTimeMs = System.currentTimeMillis()
        val originName = removeAfterLastDot(file.originalFilename ?: "")
        val fileName = originName.plus("_${currentTimeMs / 1000}${currentTimeMs % 1000}")
        val path = DIRECTORY.plus("/ddm/").plus(fileName).plus(".").plus(getFileExtension(file))
        val destFile = File(path)
        Files.write(Paths.get(destFile.path), file.bytes)
        val entity = DdmEntity(
            id = 0,
            name = file.originalFilename ?: "unknown",
            type = deviceType,
            version = version,
            path = path.replace(DIRECTORY, "/resources"),
            registerDate = LocalDateTime.now()
        )
        repository.save(entity)
        return entity
    }

    private fun createBasePath() {
        val path = Paths.get(DIRECTORY.plus("/ddm"))
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path)
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }

    /**
     * MultipartFile 에 확장자 리턴하는 함수
     * image -> jpg, png
     * text -> text
     * @param file 업로드할 파일
     */
    fun getFileExtension(file: MultipartFile): String {
        if (file.isEmpty) throw FileNotFoundException("파일이 없습니다.")
        val originName = file.originalFilename
        if (originName.isNullOrEmpty()) throw IllegalArgumentException("파일 명이 없습니다.")
        val lastIdx = originName.lastIndexOf(".")
        if (lastIdx >= 0) {
            return originName.substring(lastIdx.plus(1))
        }
        throw IllegalArgumentException("파일 확장자가 없습니다. 파일명을 확인해주세요.")
    }

    fun removeAfterLastDot(input: String): String {
        val lastDotIndex = input.lastIndexOf(".")
        return if (lastDotIndex != -1) {
            input.substring(0, lastDotIndex)
        } else {
            input
        }
    }
}
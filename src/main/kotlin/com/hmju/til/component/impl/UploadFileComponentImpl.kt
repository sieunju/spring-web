package com.hmju.til.component.impl

import com.hmju.til.component.UploadFileComponent
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.FileNotFoundException
import java.nio.file.Files
import java.nio.file.Paths
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * Description : File Upload Component Implement Class
 *
 * Created by juhongmin on 12/31/23
 */
@Component
@Suppress("unused")
class UploadFileComponentImpl : UploadFileComponent {

    private val logger: Logger by lazy { LoggerFactory.getLogger(this.javaClass) }

    @Value("\${upload-path.image}")
    private lateinit var imageDirectory: String

    @Value("\${upload-path.txt}")
    private lateinit var textDirectory: String

    @Value("\${upload-path.audio}")
    private lateinit var audioDirectory: String

    @Value("\${upload-path.etc}")
    private lateinit var etcDirectory: String

    private val dateFormat: DateTimeFormatter by lazy { DateTimeFormatter.ofPattern("yyyyMMdd") }

    /**
     * MultipartFile contentType 에 따라 정의된 MimeType 리턴하는 함수
     * @param contentType image/jpg, image,png
     */
    override fun getMimeType(
        contentType: String?
    ): UploadFileComponent.MimeType {
        if (contentType.isNullOrEmpty()) return UploadFileComponent.MimeType.ETC
        return UploadFileComponent.MimeType.entries.toTypedArray()
            .firstOrNull { contentType.startsWith(it.value ?: "") }
            ?: UploadFileComponent.MimeType.ETC
    }

    /**
     * MultipartFile 에 확장자 리턴하는 함수
     * image -> jpg, png
     * text -> text
     * @param file 업로드할 파일
     */
    override fun getFileExtension(file: MultipartFile): String {
        if (file.isEmpty) throw FileNotFoundException("파일이 없습니다.")
        val originName = file.originalFilename
        if (originName.isNullOrEmpty()) throw IllegalArgumentException("파일 명이 없습니다.")
        val lastIdx = originName.lastIndexOf(".")
        if (lastIdx >= 0) {
            return originName.substring(lastIdx.plus(1))
        }
        throw IllegalArgumentException("파일 확장자가 없습니다. 파일명을 확인해주세요.")
    }

    /**
     * 확장자 타입에 따라 디렉토리 생성하는 함수
     * @param mimeType [image, text, audio, etc]
     */
    override fun createTypeDirectory(
        mimeType: UploadFileComponent.MimeType
    ): String? {
        val directory = when (mimeType) {
            UploadFileComponent.MimeType.IMAGE -> imageDirectory
            UploadFileComponent.MimeType.TEXT -> textDirectory
            UploadFileComponent.MimeType.AUDIO -> audioDirectory
            UploadFileComponent.MimeType.ETC -> etcDirectory
        }
        val basePath = "${directory}/${LocalDateTime.now().format(dateFormat)}/"
        val path = Paths.get(basePath)
        return if (!Files.exists(path)) {
            try {
                Files.createDirectories(path)
                basePath
            } catch (ex: Exception) {
                null
            }
        } else {
            basePath
        }
    }

    /**
     * MultipartFile 을 원하는 디렉토리에 파일 이동시키는 함수
     * @param file 업로드할 파일
     * @param targetDirectory 저장할 디렉토리
     */
    override fun moveToFile(
        file: MultipartFile,
        targetDirectory: String
    ): String {
        if (file.isEmpty) throw FileNotFoundException("파일이 없습니다.")
        val currentTimeMs = System.currentTimeMillis()
        val fileName = "${currentTimeMs / 1000}${currentTimeMs % 1000}"
        val destFile = File(
            targetDirectory
                .plus(fileName)
                .plus(".")
                .plus(getFileExtension(file))
        )
        Files.write(Paths.get(destFile.path), file.bytes)
        return destFile.path
    }
}

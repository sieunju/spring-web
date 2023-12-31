package com.hmju.til.component

import org.springframework.web.multipart.MultipartFile
import java.io.FileNotFoundException

/**
 * Description : 파일 업로드 컴포넌트
 *
 * Created by juhongmin on 12/31/23
 */
interface UploadFileComponent {
    enum class MimeType(
        val value: String? = null
    ) {
        IMAGE("image"),
        TEXT("text"),
        AUDIO("audio"),
        ETC(null)
    }

    /**
     * MultipartFile contentType 에 따라 정의된 MimeType 리턴하는 함수
     * @param contentType image/jpg, image,png
     */
    fun getMimeType(
        contentType: String?
    ): MimeType

    /**
     * MultipartFile 에 확장자 리턴하는 함수
     * image -> jpg, png
     * text -> text
     * @param file 업로드할 파일
     */
    @Throws(IllegalArgumentException::class, FileNotFoundException::class)
    fun getFileExtension(file: MultipartFile): String

    /**
     * 확장자 타입에 따라 디렉토리 생성하는 함수
     * @param mimeType [image, text, audio, etc]
     */
    fun createTypeDirectory(
        mimeType: MimeType
    ): String?

    /**
     * MultipartFile 을 원하는 디렉토리에 파일 이동시키는 함수
     * @param file 업로드할 파일
     * @param targetDirectory 저장할 디렉토리
     */
    fun moveToFile(
        file: MultipartFile,
        targetDirectory: String
    ): String
}

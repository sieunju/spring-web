package com.hmju.til.features.file.model.entity

import jakarta.persistence.*
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDateTime

/**
 * Description : File Entity
 * 다른 클래스와 겹쳐서 접미사 Entity 붙임
 * db_file > FILE_TB
 * Created by juhongmin on 12/31/23
 */
@Entity
@Table(name = "FILE_TB")
data class FileEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    val id: Long = 0,
    @Column(name = "ORG_NAME")
    val originalName: String? = null,
    @Column(name = "PATH", length = 80, nullable = false)
    val path: String = "",
//    @Lob
//    @Column(name = "OBJ")
//    val binary: ByteArray? = null,
    @Column(name = "IS_LOCK", nullable = false)
    val isLock: Boolean = false,
    @Column(name = "MIME_TYPE", length = 80)
    val mimeType: String? = null,
    @Column(name = "REG_DATE")
    val registerDate: LocalDateTime? = null,
) {
    /**
     * 파일 업로드 이미지 저장이후 Entity 생성자
     */
    constructor(
        file: MultipartFile,
        uploadPath: String,
    ) : this(
        id = 0,
        originalName = file.originalFilename,
        path = uploadPath,
        // binary = file.bytes, // 고민후 삭제 할 예정
        mimeType = file.contentType,
        registerDate = LocalDateTime.now(),
    )
}
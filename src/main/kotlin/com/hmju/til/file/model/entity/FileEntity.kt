package com.hmju.til.file.model.entity

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
    val id: Int = 0,
    @Column(name = "ORG_NAME")
    val originalName: String? = null,
    @Column(name = "PATH", length = 80, nullable = false)
    val path: String = "",
    @Lob
    @Column(name = "OBJ")
    val binary: ByteArray? = null,
    @Column(name = "IS_LOCK", nullable = false)
    val isLock: Boolean = false,
    @Column(name = "MIME_TYPE", length = 80)
    val mimeType: String? = null,
    @Column(name = "REG_DATE")
    val registerDate: LocalDateTime? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FileEntity

        if (id != other.id) return false
        if (originalName != other.originalName) return false
        if (path != other.path) return false
        if (binary != null) {
            if (other.binary == null) return false
            if (!binary.contentEquals(other.binary)) return false
        } else if (other.binary != null) return false
        if (isLock != other.isLock) return false
        if (mimeType != other.mimeType) return false
        if (registerDate != other.registerDate) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + (originalName?.hashCode() ?: 0)
        result = 31 * result + path.hashCode()
        result = 31 * result + (binary?.contentHashCode() ?: 0)
        result = 31 * result + isLock.hashCode()
        result = 31 * result + (mimeType?.hashCode() ?: 0)
        result = 31 * result + (registerDate?.hashCode() ?: 0)
        return result
    }

    /**
     * 파일 업로드 이미지 저장이후 Entity 생성자
     */
    constructor(
        file: MultipartFile,
        uploadPath: String
    ) : this(
        id = 0,
        originalName = file.originalFilename,
        path = uploadPath,
        binary = file.bytes, // 고민후 삭제 할 예정
        mimeType = file.contentType,
        registerDate = LocalDateTime.now()
    )
}

package com.hmju.til.features.file.impl

import com.hmju.til.component.UploadFileComponent
import com.hmju.til.core.exception.FileCleaningException
import com.hmju.til.core.model.PaginationMeta
import com.hmju.til.features.file.FileRepository
import com.hmju.til.features.file.FileService
import com.hmju.til.features.file.model.dto.FileCleaningDTO
import com.hmju.til.features.file.model.entity.FileEntity
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.nio.file.Files
import java.nio.file.NoSuchFileException
import java.nio.file.Paths
import kotlin.io.path.writeBytes

/**
 * Description : File Service Impl Class
 *
 * Created by juhongmin on 12/31/23
 */
@Service
@Suppress("unused")
class FileServiceImpl @Autowired constructor(
    private val repository: FileRepository,
    private val component: UploadFileComponent
) : FileService {

    private val logger: Logger by lazy { LoggerFactory.getLogger(this.javaClass) }

    /**
     * 메모 데이터 조회
     * @param pageNo 페이지 번호 (1부터 시작)
     * @param pageSize 페이지 사이즈
     */
    override fun fetch(
        pageNo: Int,
        pageSize: Int,
    ): List<FileEntity> {
//        val pageable = PageRequest.of(
//            pageNo.minus(1).coerceAtLeast(0),
//            pageSize,
//            Sort.by("id").ascending(),
//        )
//        return repository.findAll(pageable).content
        // Start Index 계산
        var offset = Math.max(pageNo.minus(1), 0)
        offset *= pageSize
        return repository.findRange(offset, pageSize)
            .map { FileEntity(it) }

    }

    /**
     * 업로드한 파일 메타 조회
     * @param pageNo 페이지 번호 (1부터 시작)
     * @param pageSize 페이지 사이즈
     */
    override fun fetchMeta(
        pageNo: Int,
        pageSize: Int,
    ): PaginationMeta {
        val count = repository.count()
        val no = 1.coerceAtLeast(pageNo) // pageNo 0 으로 줄때 대응
        var maxPage = count / pageSize
        // 나머지가 있는 경우 1추가
        if (count % pageSize > 0) {
            maxPage++
        }
        val nextPage = if (maxPage > no) {
            no.plus(1)
        } else {
            null
        }
        return PaginationMeta(
            totalCount = count.toInt(),
            nextPage = nextPage,
            currentPage = pageNo
        )
    }

    /**
     * 업로드할 파일 넣기
     * @param list DB에 넣을 정보
     */
    override fun postAll(list: List<MultipartFile>): List<FileEntity> {
        return list
            .mapNotNull { handleFileUpload(it) }
            .map { repository.save(it) }
    }

    /**
     * 파일 리소스 클리닝 작업 하는 함수
     */
    @Transactional(value = "fileTransactionManagerFactory", rollbackFor = [FileCleaningException::class])
    override fun cleaning(): FileCleaningDTO {
        val findAllFiles = getResourceFiles()
        val findAllDb = repository.findAll()
        // [s] DB 에 있는 바이너리들 리소스에 저장
        val filePaths = findAllFiles
            .map { it.path.replace("src/main/resources/files", "/resources") }
        findAllDb.forEach { entity ->
            if (entity.binary != null && !filePaths.contains(entity.path)) {
                try {
                    val path = "./src/main/resources/files${entity.path.replace("/resources", "")}"
                    try {
                        Paths.get(path.substring(0, path.lastIndexOf("/"))).toFile().mkdirs()
                    } catch (ex: Exception) {
                        // ignore
                    }
                    Files.createFile(Paths.get(path))
                        .runCatching { this.writeBytes(entity.binary) }
                        .onSuccess { logger.info("파일 저장 성공 ${entity.path}") }
                        .onFailure { logger.error("파일 저장 실패 ${entity.path}") }
                } catch (ex: NoSuchFileException) {
                    logger.info("경로 생성 ${ex.file}")
                }
            }
        }
        // [e] DB 에 있는 바이너리들 리소스에 저장

        val addEntityList = mutableListOf<FileEntity>()
        val removeEntityList = mutableListOf<FileEntity>()
        // [s] 중복된 이미지 경로 삭제 처리
        /*val duplicateList = mutableListOf<String>()
        findAllDb.forEach {
            if (duplicateList.contains(it.path)) {
                repository.deleteById(it.id.toInt())
            } else {
                duplicateList.add(it.path)
            }
        }*/
        // [e] 중복된 이미지 경로 삭제 처리

        // 전체 파일등중 DB에 없는 리소스 파일 추가
        /*findAllFiles.forEach { file ->
            val newPath = file.path.replace("src/main/resources/files", "")
            val findDb = findAllDb.find { it.path.contains(newPath) }
            if (findDb == null) {
                // 전체 파일중 DB에 없는 타입
                addEntityList.add(FileEntity(file))
            } else if (findDb.binary == null) {
                // 바이너리가 없는 경우
                addEntityList.add(findDb.copy(binary = Files.readAllBytes(file.toPath())))
            }
        }

        addEntityList.forEach {
            try {
                repository.save(it)
            } catch (ex: Exception) {
                logger.error("SaveEntity ${it.id} ${it.path} $ex")
            }
        }

        val resourceFilePaths = findAllFiles
            .map { it.path.replace("src/main/resources/files", "") }
            .toMutableSet()

        // DB에서 실제 리스소 파일에 없는 것들 제거
        findAllDb.forEach { entity ->
            val findEntity = resourceFilePaths
                .find { entity.path.contains(it) }
            if (findEntity == null) {
                removeEntityList.add(entity)
            }
        }

        try {
            repository.deleteAll(removeEntityList)
        } catch (ex: Exception) {
            logger.error("Delete Entity Error $ex")
            throw FileCleaningException.Code.DELETE()
        }*/

        return FileCleaningDTO(
            addFileSize = addEntityList.size,
            removeFileSize = removeEntityList.size
        )
    }

    /**
     * 파일 업로드 처리하는 함수
     *
     * @param file 업로드할 파일
     */
    private fun handleFileUpload(file: MultipartFile): FileEntity? {
        val mimeType = component.getMimeType(file.contentType)
        val directory = component.createTypeDirectory(mimeType) ?: return null
        val uploadPath = component.moveToFile(file, directory)
        return FileEntity(file, uploadPath)
    }

    /**
     * 실제 리소스 디렉토리에 파일들 조회 하는 함수
     */
    @Throws(FileCleaningException::class)
    private fun getResourceFiles(): List<File> {
        return try {
            val path = Paths.get("./src/main/resources/files")
            Files.walk(path)
                .map { it.normalize() }
                .filter { Files.isRegularFile(it) && !it.endsWith(".DS_Store") }
                .map { it.toFile() }
                .toList()
        } catch (e: Exception) {
            throw FileCleaningException.Code.FILE_RESOURCE()
        }
    }
}
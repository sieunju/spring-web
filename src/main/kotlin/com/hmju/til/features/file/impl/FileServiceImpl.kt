package com.hmju.til.features.file.impl

import com.hmju.til.component.UploadFileComponent
import com.hmju.til.core.model.PaginationMeta
import com.hmju.til.features.file.FileRepository
import com.hmju.til.features.file.FileService
import com.hmju.til.features.file.model.entity.FileEntity
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

/**
 * Description : File Service Impl Class
 *
 * Created by juhongmin on 12/31/23
 */
@Service
@Suppress("unused")
class FileServiceImpl
    @Autowired
    constructor(
        private val repository: FileRepository,
        private val component: UploadFileComponent,
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
            val pageable =
                PageRequest.of(
                    pageNo.minus(1).coerceAtLeast(0),
                    pageSize,
                    Sort.by("id").ascending(),
                )
            return repository.findAll(pageable).content
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
            val nextPage =
                if (maxPage > no) {
                    no.plus(1)
                } else {
                    null
                }
            return PaginationMeta(
                totalCount = count.toInt(),
                nextPage = nextPage,
                currentPage = pageNo,
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
    }
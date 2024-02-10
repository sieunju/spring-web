package com.hmju.til.features.file

import com.hmju.til.core.model.PaginationMeta
import com.hmju.til.features.file.model.entity.FileEntity
import org.springframework.web.multipart.MultipartFile

/**
 * Description : File Uploads Service Class
 *
 * Created by juhongmin on 12/31/23
 */
interface FileService {

    /**
     * 업로드한 파일 데이터 조회
     * @param pageNo 페이지 번호 (1부터 시작)
     * @param pageSize 페이지 사이즈
     */
    fun fetch(
        pageNo: Int,
        pageSize: Int
    ): List<FileEntity>

    /**
     * 업로드한 파일 메타 조회
     * @param pageNo 페이지 번호 (1부터 시작)
     * @param pageSize 페이지 사이즈
     */
    fun fetchMeta(
        pageNo: Int,
        pageSize: Int
    ): PaginationMeta

    /**
     * 업로드할 파일 넣기
     * @param list DB에 넣을 정보
     */
    fun postAll(
        list: List<MultipartFile>
    ): List<FileEntity>
}

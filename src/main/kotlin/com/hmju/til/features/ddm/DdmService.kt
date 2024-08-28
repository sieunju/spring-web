package com.hmju.til.features.ddm

import com.hmju.til.features.ddm.model.entity.DdmEntity
import org.springframework.web.multipart.MultipartFile

/**
 * Description : DDM Controller (바람처럼 왔다가 바람처럼 가는 기능)
 *
 * Created by juhongmin on 08/28/24
 */
interface DdmService {
    fun fetch(): List<DdmEntity>

    fun save(
        deviceType: String,
        version: String,
        file: MultipartFile
    ): DdmEntity
}
package com.hmju.til.features.file

import com.hmju.til.features.file.model.entity.FileEntity
import org.springframework.data.jpa.repository.JpaRepository

/**
 * Description : File Repository
 *
 * Created by juhongmin on 12/31/23
 */
interface FileRepository : JpaRepository<FileEntity, Int>
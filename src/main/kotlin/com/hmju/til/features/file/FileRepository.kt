package com.hmju.til.features.file

import com.hmju.til.features.file.model.entity.FileEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

/**
 * Description : File Repository
 *
 * Created by juhongmin on 12/31/23
 */
interface FileRepository : JpaRepository<FileEntity, Int> {
    @Query(
        value = "SELECT \n" +
            "\tft.ID, \n" +
            "\tft.ORG_NAME, \n" +
            "\tft.`PATH`, \n" +
            "\tft.MIME_TYPE, \n" +
            "\tft.REG_DATE  \n" +
            "\tFROM FILE_TB ft  \n" +
            "ORDER BY ft.ID ASC\n" +
            "LIMIT :limit OFFSET :offset",
        nativeQuery = true
    )
    fun findRange(
        offset: Int,
        limit: Int
    ): List<FileEntity>
}

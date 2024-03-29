package com.hmju.til.features.android.model.entity

import com.hmju.til.features.android.model.dto.AosMemoDTO
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

/**
 * Description : Android Memo Entity
 *
 * Created by juhongmin on 1/15/24
 */
@Entity
@Table(name = "AND_MEMO_TB")
data class AosMemo(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    val id: Long = 0,
    @Column(name = "TITLE", nullable = false)
    val title: String = "",
    @Column(name = "CONTENTS", nullable = false)
    val contents: String = "",
    @Column(name = "REGISTER_DATE")
    val registerDate: LocalDateTime? = null,
) {
    constructor(
        dto: AosMemoDTO,
    ) : this(
        id = dto.id ?: 0,
        title = dto.title,
        contents = dto.contents,
        registerDate = dto.registerDate,
    )
}
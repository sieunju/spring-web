package com.hmju.til.memo.model.entity

import com.hmju.til.memo.model.dto.MemoDTO
import jakarta.persistence.*
import java.time.LocalDateTime

/**
 * Description : Memo Entity
 *
 * Created by juhongmin on 12/22/23
 */
@Entity
@Table(name = "MEMO_TB")
data class Memo(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMO_ID", nullable = false)
    val id: Long = 0,
    @Column(name = "USER_ID", length = 30, nullable = false)
    val userId: String = "",
    @Column(name = "TAG")
    val tag: Int? = null,
    @Column(name = "NUM")
    val num: Int? = null,
    @Column(name = "TITLE", length = 200, nullable = false)
    val title: String = "",
    @Column(name = "CONTENTS", length = 800, nullable = false)
    val contents: String = "",
    @Column(name = "REGISTER_DATE")
    val registerDate: LocalDateTime? = null
) {
    constructor(
        entity: MemoDTO
    ) : this(
        id = entity.id ?: 0,
        userId = entity.userId ?: "unknown",
        tag = entity.tag,
        num = null,
        title = entity.title,
        contents = entity.contents,
        registerDate = entity.registerDate
    )
}

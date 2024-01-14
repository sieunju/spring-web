package com.hmju.til.goods.model.entity

import com.hmju.til.goods.model.dto.GoodsDTO
import jakarta.persistence.*

/**
 * Description : Test 용 상품 데이터 모델
 *
 * Created by juhongmin on 1/14/24
 */
@Entity
@Table(name = "GOODS_TB")
data class Goods(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    val id: Long = 0,
    @Column(name = "TITLE", nullable = false)
    val title: String = "",
    @Column(name = "MESSAGE", nullable = false)
    val message: String = "",
    @Column(name = "IMAGE_PATH", nullable = false)
    val imagePath: String = ""
) {
    constructor(
        dto: GoodsDTO
    ) : this(
        id = dto.id ?: 0,
        title = dto.title,
        message = dto.message,
        imagePath = dto.imagePath
    )
}

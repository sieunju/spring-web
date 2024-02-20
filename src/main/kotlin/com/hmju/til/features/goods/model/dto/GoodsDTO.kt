package com.hmju.til.features.goods.model.dto

import com.hmju.til.features.goods.model.entity.Goods
import com.hmju.til.features.goods.model.vo.GoodsVO

/**
 * Description : Test 용 상품 데이터 모델
 *
 * Created by juhongmin on 1/14/24
 */
data class GoodsDTO(
    val id: Long? = null,
    val title: String,
    val message: String,
    val imagePath: String,
) {
    constructor(
        entity: Goods,
    ) : this(
        id = entity.id,
        title = entity.title,
        message = entity.message,
        imagePath = entity.imagePath,
    )

    constructor(
        vo: GoodsVO,
    ) : this(
        id = vo.id,
        title = vo.title,
        message = vo.message,
        imagePath = vo.imagePath,
    )
}
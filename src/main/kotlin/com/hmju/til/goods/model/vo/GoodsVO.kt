package com.hmju.til.goods.model.vo

/**
 * Description : 클라이언트에서 들어오는 데이터 모델
 *
 * @param id ID
 * @param title 상품 명
 * @param message 상품 메시지
 * @param imagePath 상품 이미지
 *
 * Created by juhongmin on 1/13/24
 */
data class GoodsVO(
    val id: Long? = null,
    val title: String = "",
    val message: String = "",
    val imagePath: String = ""
)

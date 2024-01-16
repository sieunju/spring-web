package com.hmju.til.features.goods

import com.hmju.til.features.goods.model.entity.Goods
import org.springframework.data.jpa.repository.JpaRepository

/**
 * Description : Goods Repository
 *
 * Created by juhongmin on 1/14/24
 */
interface GoodsRepository : JpaRepository<Goods, Long>

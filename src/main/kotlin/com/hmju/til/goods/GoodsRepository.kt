package com.hmju.til.goods

import com.hmju.til.goods.model.entity.Goods
import org.springframework.data.jpa.repository.JpaRepository

/**
 * Description : Goods Repository
 *
 * Created by juhongmin on 1/14/24
 */
interface GoodsRepository : JpaRepository<Goods, Long>

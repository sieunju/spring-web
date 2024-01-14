package com.hmju.til.jsend.model

import com.hmju.til.core.model.JSendMeta
import kotlin.random.Random

/**
 * Description : JSend Test Meta
 *
 * Created by juhongmin on 1/13/24
 */
data class TestMeta(
    val metaSize: Int = Random.nextInt()
) : JSendMeta()

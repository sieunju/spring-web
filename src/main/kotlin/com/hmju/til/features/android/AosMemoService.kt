package com.hmju.til.features.android

import com.hmju.til.features.android.model.dto.AosMemoDTO
import com.hmju.til.features.android.model.entity.AosMemo

/**
 * Description : Android Memo Service
 *
 * Created by juhongmin on 1/15/24
 */
interface AosMemoService {
    fun fetch(): List<AosMemo>

    fun post(vo: AosMemoDTO): AosMemo
}

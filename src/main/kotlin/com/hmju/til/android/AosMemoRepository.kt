package com.hmju.til.android

import com.hmju.til.android.model.entity.AosMemo
import org.springframework.data.jpa.repository.JpaRepository

/**
 * Description : Android Memo Repository
 *
 * Created by juhongmin on 1/15/24
 */
interface AosMemoRepository : JpaRepository<AosMemo, Long>

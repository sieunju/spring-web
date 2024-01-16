package com.hmju.til.features.android.impl

import com.hmju.til.features.android.AosMemoRepository
import com.hmju.til.features.android.AosMemoService
import com.hmju.til.features.android.model.dto.AosMemoDTO
import com.hmju.til.features.android.model.entity.AosMemo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * Description : Android Memo Service Impl Class
 *
 * Created by juhongmin on 1/15/24
 */
@Service
@Suppress("unused")
class AosMemoServiceImpl @Autowired constructor(
    private val repository: AosMemoRepository
) : AosMemoService {
    override fun fetch(): List<AosMemo> {
        return repository.findAll()
    }

    override fun post(vo: AosMemoDTO): AosMemo {
        return repository.save(AosMemo(vo))
    }
}

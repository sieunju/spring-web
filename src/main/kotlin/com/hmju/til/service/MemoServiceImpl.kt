package com.hmju.til.service

import com.hmju.til.model.entity.MemoEntity
import com.hmju.til.repository.MemoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * Description :
 *
 * Created by juhongmin on 12/22/23
 */

@Service
class MemoServiceImpl @Autowired constructor(
    private val repository: MemoRepository
) {

    fun fetch(): List<MemoEntity> {
        return repository.findAll().toList()
    }
}

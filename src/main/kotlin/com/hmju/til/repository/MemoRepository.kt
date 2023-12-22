package com.hmju.til.repository

import com.hmju.til.model.entity.MemoEntity
import org.springframework.data.repository.CrudRepository

/**
 * Description : Memo Repository
 *
 * Created by juhongmin on 12/22/23
 */
interface MemoRepository : CrudRepository<MemoEntity,Long> {
}

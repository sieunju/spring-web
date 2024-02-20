package com.hmju.til.component

/**
 * Description : Redis Cache DAO
 *
 * Created by juhongmin on 1/21/24
 */
interface RedisDao {
    fun <T> setValue(
        key: String,
        data: T,
    )

    fun addSet(
        key: String,
        data: String,
    )

    fun getString(key: String): String

    fun removeKey(key: String)
}
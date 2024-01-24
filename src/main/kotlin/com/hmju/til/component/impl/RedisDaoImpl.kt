package com.hmju.til.component.impl

import com.hmju.til.component.RedisDao
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.SetOperations
import org.springframework.data.redis.core.ValueOperations
import org.springframework.stereotype.Component
import java.io.Serializable

/**
 * Description : Redis Dao Implement Class
 *
 * Created by juhongmin on 1/21/24
 */
@Component
@Suppress("unused")
internal class RedisDaoImpl @Autowired constructor(
    private val template: RedisTemplate<String, String>
) : RedisDao {
    private val stringOperations: ValueOperations<String, String> by lazy { template.opsForValue() }
    private val setOperations: SetOperations<String, String> by lazy { template.opsForSet() }
    override fun <T> setValue(key: String, data: T) {
        when (data) {
            is String -> {
                stringOperations.set(key,data)
            }

            is Serializable -> {
            }
        }
    }

    override fun addSet(key: String, data: String) {
        template.opsForSet().add(key, data)
    }

    override fun getString(key: String): String {
        return template.opsForValue().get(key) ?: ""
    }

    override fun removeKey(key: String) {
        template.delete(key)
    }
}
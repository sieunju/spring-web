// package com.hmju.til.core.config
//
// import org.springframework.beans.factory.annotation.Qualifier
// import org.springframework.beans.factory.annotation.Value
// import org.springframework.context.annotation.Bean
// import org.springframework.context.annotation.Configuration
// import org.springframework.data.redis.connection.RedisConnectionFactory
// import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
// import org.springframework.data.redis.core.RedisTemplate
// import org.springframework.data.redis.repository.configuration.EnableRedisRepositories
// import org.springframework.data.redis.serializer.RedisSerializer
//
// /**
// * Description : Redis 인메모리 캐시 서버 Config
// * 레디스는 아직 추가할 기능이 아니므로 주석 처리합니다.
// * Created by juhongmin on 1/21/24
// */
// @Suppress("unused")
// @Configuration
// @EnableRedisRepositories
// class RedisConfig {
//
//    @Value("\${spring.redis.host}")
//    private lateinit var host: String
//
//    @Value("\${spring.redis.port}")
//    private lateinit var port: String
//
//    @Bean("redisConnectionFactory")
//    fun redisConnectionFactory(): RedisConnectionFactory {
//        return LettuceConnectionFactory(host, port.toInt())
//    }
//
//    @Bean
//    fun redisTemplate(
//        @Qualifier("redisConnectionFactory") factory: RedisConnectionFactory
//    ): RedisTemplate<String, String> {
//        val template = RedisTemplate<String, String>()
//        template.setDefaultSerializer(RedisSerializer.json())
//        template.keySerializer = RedisSerializer.string()
//        template.hashKeySerializer = RedisSerializer.string()
//        template.connectionFactory = factory
//        return template
//    }
// }

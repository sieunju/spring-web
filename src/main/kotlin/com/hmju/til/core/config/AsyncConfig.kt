package com.hmju.til.core.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor

/**
 * Description : 비동기로 처리해야하는 API 를 위한 Config
 *
 * Created by juhongmin on 2/18/24
 */
@Suppress("unused")
@EnableAsync
@Configuration
class AsyncConfig {
    @Bean(name = ["controllerExecutor"], destroyMethod = "shutdown")
    fun initControllerTaskExecutor(): ThreadPoolTaskExecutor {
        val executor = ThreadPoolTaskExecutor()
        executor.corePoolSize = 1
        executor.maxPoolSize = 20
        executor.setThreadNamePrefix("async-task")
        return executor
    }
}
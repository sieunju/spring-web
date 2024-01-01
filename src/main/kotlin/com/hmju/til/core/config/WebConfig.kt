package com.hmju.til.core.config

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Configuration
import org.springframework.http.CacheControl
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import java.util.concurrent.TimeUnit

/**
 * Description : Web Config
 *
 * Created by juhongmin on 12/31/23
 */
@Configuration
@Suppress("unused")
class WebConfig : WebMvcConfigurer {

    private val logger: Logger by lazy { LoggerFactory.getLogger(this.javaClass) }

    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        // File Resource Config
        registry.addResourceHandler("/resources/**")
            .addResourceLocations("file:src/main/resources/files/")
            .setCacheControl(CacheControl.maxAge(10, TimeUnit.MINUTES))
    }

//    override fun addCorsMappings(registry: CorsRegistry) {
//        registry.addMapping("/api/**")
//            .allowedOrigins("*")
//            .allowedMethods("GET", "POST", "PUT", "DELETE")
//            .allowedHeaders("Authorization", "Content-Type")
//            .allowCredentials(true)
//            .maxAge(3600)
//    }
}

package com.hmju.til.core.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import io.swagger.v3.core.jackson.ModelResolver
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.security.SecurityScheme
import io.swagger.v3.oas.annotations.security.SecuritySchemes
import io.swagger.v3.oas.annotations.servers.Server
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springdoc.core.models.GroupedOpenApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Description : Swagger Config
 *
 * Created by juhongmin on 12/23/23
 */
@Configuration
@OpenAPIDefinition(
    info = Info(title = "TIL API 문서", version = "v1.0.0"),
    servers = [
        Server(url = "http://localhost:10004", description = "local"),
        Server(url = "https://til.qtzz.synology.me", description = "prod"),
    ],
)
@SecuritySchemes(
    value = [
        SecurityScheme(
            type = SecuritySchemeType.HTTP,
            name = "JWT Auth",
            scheme = "Bearer",
            bearerFormat = "JWT",
        ),
    ],
)
@Suppress("unused")
class SwaggerConfig {
    private val logger: Logger by lazy { LoggerFactory.getLogger(this.javaClass) }

    @Bean
    fun apiDocs(): GroupedOpenApi {
        // URL swagger-ui/index.html
        return GroupedOpenApi.builder()
            .group("API DOCS")
            .pathsToMatch("/api/**")
            .build()
    }

    @Bean
    fun modelResolver(objectMapper: ObjectMapper): ModelResolver {
        return ModelResolver(objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE))
    }
}
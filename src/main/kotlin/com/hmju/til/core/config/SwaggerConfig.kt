package com.hmju.til.core.config

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.security.SecurityRequirements
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
        Server(url = "https://til.qtzz.synology.me", description = "prod")
    ]
)
@SecurityRequirements(value = [SecurityRequirement(name = "JSON Web Token Auth")])
@SecuritySchemes(
    value = [
        SecurityScheme(
            type = SecuritySchemeType.HTTP,
            name = "JSON Web Token Auth",
            scheme = "bearer",
            bearerFormat = "JWT"
        )
    ]
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
}

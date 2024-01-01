package com.hmju.til.core.config

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import io.swagger.v3.oas.models.servers.Server
import org.springdoc.core.models.GroupedOpenApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.EnableWebMvc

/**
 * Description : Swagger Config
 *
 * Created by juhongmin on 12/23/23
 */
@Configuration
@OpenAPIDefinition(
    info = Info(
        title = "TIL API 문서",
        description = "개인 API 문서입니다.",
        version = "v1.0.0"
    ),
//    servers = [Server(
//        url = "http://localhost:10004",
//        description = "local"
//    ), Server(
//        url = "https://til.qtzz.synology.me",
//        description = "prod"
//    )]
)
@EnableWebMvc
@Suppress("unused")
class SwaggerConfig {

    @Bean
    fun openApi(): OpenAPI {
        val jwtSchemeName = "jwtAuth"
        val securityRequirement = SecurityRequirement().addList(jwtSchemeName)

        val component = Components()
            .addSecuritySchemes(
                jwtSchemeName, SecurityScheme()
                    .name(jwtSchemeName)
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("bearer")
                    .bearerFormat("JWT")
            )
        return OpenAPI()
            .servers(listOf(getLocalServer(), getProdServer()))
            .addSecurityItem(securityRequirement)
            .components(component)
    }

    private fun getLocalServer(): Server {
        return Server().apply {
            url = "http://localhost:10004"
            description = "local"
        }
    }

    private fun getProdServer(): Server {
        return Server().apply {
            url = "https://til.qtzz.synology.me"
            description = "prod"
        }
    }

    @Bean
    fun apiDocs(): GroupedOpenApi {
        return GroupedOpenApi.builder()
            .group("API DOCS")
            .pathsToMatch("/api/**")
            .build()
    }
}

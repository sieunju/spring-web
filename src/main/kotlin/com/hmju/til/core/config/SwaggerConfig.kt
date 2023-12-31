package com.hmju.til.core.config

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springdoc.core.models.GroupedOpenApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.Server

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
    )
)
@EnableWebMvc
@Suppress("unused")
class SwaggerConfig {

//    @Bean
//    fun api(): Docket {
//        // http://127.0.0.1:10004/swagger-ui/index.html
//        return Docket(DocumentationType.OAS_30)
//            .servers(getLocalServer(), getProductionServer())
//            // .apiInfo(apiInfo())
//            .select()
//            .apis(RequestHandlerSelectors.basePackage("com.hmju.til"))
//            .paths(PathSelectors.ant("/api/**"))
//            .build()
//            .groupName("API 1.0.0")
//    }

    @Bean
    fun openApi(): OpenAPI {
        val jwtSchemeName = "jwtAuth"
        val securityRequirement = SecurityRequirement().addList(jwtSchemeName)

        val component = Components()
            .addSecuritySchemes(jwtSchemeName, SecurityScheme()
                .name(jwtSchemeName)
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
            )
        return OpenAPI()
            .addSecurityItem(securityRequirement)
            .components(component)
    }

    @Bean
    fun apiDocs(): GroupedOpenApi {
        return GroupedOpenApi.builder()
            .group("API DOCS")
            .pathsToMatch("/api/**")
            .build()
    }

    private fun apiInfo(): ApiInfo {
        return ApiInfoBuilder()
            .title("Spring Web API")
            .description("TIL API Server Docs")
            .version("v1.0.0")
            .build()
    }

    private fun getLocalServer(): Server {
        return Server(
            "local",
            "http://localhost:10004",
            "local server",
            emptyList(),
            emptyList()
        )
    }

    private fun getProductionServer(): Server {
        return Server(
            "production",
            "https://til.qtzz.synology.me",
            "production server",
            emptyList(),
            emptyList()
        )
    }
}

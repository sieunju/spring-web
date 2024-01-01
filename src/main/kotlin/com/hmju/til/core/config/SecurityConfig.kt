package com.hmju.til.core.config

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import java.util.*


/**
 * Description : Security Config
 *
 * Created by juhongmin on 1/1/24
 */
@Configuration
@EnableWebSecurity
@Suppress("unused")
class SecurityConfig {

    private val logger: Logger by lazy { LoggerFactory.getLogger(this.javaClass) }

    @Primary
    @Bean("corsConfigurationSource")
    fun corsConfigurationSource(): CorsConfigurationSource {
        return CorsConfigurationSource { req ->
            logger.info("Security ${req.method} ${req.requestURI}")
            logger.info("Cors Headers ${req.headerNames}")
            val config = CorsConfiguration()
            config.allowedHeaders = Collections.singletonList("*")
            config.allowedMethods = Collections.singletonList("*")
            config.setAllowedOriginPatterns(Collections.singletonList("http://localhost:10004"))
            config.allowCredentials = true
            return@CorsConfigurationSource config
        }
    }

    @Bean
    fun securityFilterChain(
        http: HttpSecurity,
        @Qualifier("corsConfigurationSource") corsConfiguration: CorsConfigurationSource
    ): SecurityFilterChain {
        http.httpBasic { it.disable() }
        http.csrf { it.disable() }
        http.cors {
            it.configurationSource(corsConfiguration)
        }
        http.sessionManagement {
            it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        }
        http.authorizeHttpRequests {
            it.requestMatchers("/api/**").permitAll()
            it.requestMatchers("/v3/api-docs/**").permitAll()
            it.requestMatchers("/swagger-ui/**").permitAll()
            // it.anyRequest().permitAll()
            // it.requestMatchers("/swagger-ui/index.html").permitAll()
            // it.anyRequest().permitAll()
        }
        return http.build()
    }
}

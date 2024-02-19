package com.hmju.til.core.config

import com.hmju.til.component.JwtComponent
import com.hmju.til.core.filter.JwtAuthenticationFilter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.csrf.CsrfFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.filter.CharacterEncodingFilter

/**
 * Description : Security Config
 *
 * Created by juhongmin on 1/1/24
 */
@Configuration
@EnableWebSecurity
@Suppress("unused")
class SecurityConfig
    @Autowired
    constructor(
        private val jwtComponent: JwtComponent,
    ) {
        @Bean
        fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
            return http
                .cors {
                    it.configurationSource {
                        val cors = CorsConfiguration()
                        cors.allowedOrigins = listOf("*")
                        cors.allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        cors.allowedHeaders = listOf("*")
                        cors
                    }
                }
                .csrf { it.disable() }
                .formLogin { it.disable() }
                .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
                .headers { headers ->
                    headers.frameOptions { it.sameOrigin() }
                    headers.cacheControl { it.disable() }
                }
                .addFilterBefore(
                    CharacterEncodingFilter(Charsets.UTF_8.toString()).apply {
                        setForceEncoding(true)
                    },
                    CsrfFilter::class.java,
                )
                .addFilterBefore(
                    JwtAuthenticationFilter(jwtComponent),
                    UsernamePasswordAuthenticationFilter::class.java,
                )
                .authorizeHttpRequests {
                    it.requestMatchers("/api/v1/auth/token").permitAll()
                    it.requestMatchers("/api/v1/auth/refresh").permitAll()
                    it.requestMatchers("/api/v1/memo/aos/**").permitAll()

                    // it.requestMatchers("/api/**").authenticated()
                    it.requestMatchers("/v3/api-docs/**").permitAll()
                    it.requestMatchers("/swagger-ui/**").permitAll()
                    it.requestMatchers("/resources/**").permitAll()

                    // View Config
                    it.requestMatchers("/views/**").permitAll()
                    it.requestMatchers("/public/css/**").permitAll()
                    it.requestMatchers("/public/js/**").permitAll()
                    it.requestMatchers("/public/resource/**").permitAll()
                    it.anyRequest().authenticated()
                }
                .build()
        }
    }
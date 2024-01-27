package com.hmju.til.component.impl

import com.hmju.til.component.JwtComponent
import com.hmju.til.core.exception.InvalidJwtException
import com.hmju.til.features.auth_jwt.model.entity.JsonWebToken
import com.hmju.til.features.auth_jwt.model.entity.JwtInfo
import com.hmju.til.features.auth_jwt.model.vo.AuthVo
import com.hmju.til.toLocalDateTime
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.security.Key
import java.util.*

/**
 * Description : JsonWebToken Provider Class
 *
 * Created by juhongmin on 1/16/24
 */
@Component
@Suppress("unused")
internal class JwtComponentImpl : JwtComponent {

    private val logger: Logger by lazy { LoggerFactory.getLogger(this.javaClass) }

    @Value("\${jwt.secret-key}")
    private lateinit var secretKey: String
    private val key: Key by lazy {
        val keyBytes: ByteArray = Decoders.BASE64.decode(secretKey)
        Keys.hmacShaKeyFor(keyBytes)
    }

    // 30분
    private val refreshExpiredTimeMs: Long by lazy { 30 * 60 * 1000 }
    private val minuteTimeMs: Long by lazy { 60 * 1000 }

    /**
     * Create Json Web Token
     * @param vo RequestBody
     * @return JWT 에 대한 정보를 리턴하는 데이터 모델
     */
    override fun create(
        vo: AuthVo
    ): JwtInfo {
        val now = Date(System.currentTimeMillis())
        val expired = Date(now.time.plus(vo.expiredMinute * minuteTimeMs))
        val refreshExpired = Date(now.time.plus(refreshExpiredTimeMs))
        val token = createToken(vo.email, now, expired)
        val refreshToken = createToken(vo.email, now, refreshExpired)
        return JwtInfo(
            token = token,
            expiredDate = expired.toLocalDateTime(),
            email = vo.email,
            refreshToken = refreshToken,
            refreshExpiredDate = refreshExpired.toLocalDateTime()
        )
    }

    override fun create(
        entity: JsonWebToken
    ): JwtInfo {
        val now = Date(System.currentTimeMillis())
        val expired = Date(now.time.plus(5 * minuteTimeMs))
        val refreshExpired = Date(now.time.plus(refreshExpiredTimeMs))
        val token = createToken(entity.email, now, expired)
        val refreshToken = createToken(entity.email, now, refreshExpired)
        return JwtInfo(
            token = token,
            expiredDate = expired.toLocalDateTime(),
            email = entity.email,
            refreshToken = refreshToken,
            refreshExpiredDate = refreshExpired.toLocalDateTime()
        )
    }

    override fun getHeaderToken(
        req: HttpServletRequest
    ): String? {
        val auth = req.getHeader(HttpHeaders.AUTHORIZATION)
        if (auth.isNullOrEmpty()) return null
        return getHeaderToken(auth)
    }

    override fun getHeaderToken(auth: String): String {
        // bearer
        return if (auth.startsWith("Bearer ")) {
            auth.substring(7)
                .replace("+", "-")
                .replace("/", "_")
                .replace("=", "")
        } else {
            throw InvalidJwtException(auth)
        }
    }

    override fun isValidate(
        token: String
    ): Boolean {
        return try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token)
            true
        } catch (ex: Exception) {
            logger.info("우리가 쓰고 있는 토큰이 아닙니다. $ex")
            false
        }
    }

    override fun isExpired(token: String): Boolean {
        return try {
            val claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .body
            !claims.expiration.before(Date())
        } catch (ex: Exception) {
            false
        }
    }

    override fun getAuthentication(token: String): Authentication {
        val subject = Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .body.subject
        return UsernamePasswordAuthenticationToken(
            subject,
            token,
            arrayListOf()
        )
    }

    /**
     * JWT Token 생성하는 함수
     * @param email Token 에 필요한 이메일
     * @param now 현재 시각
     * @param expired 만료 시간
     */
    private fun createToken(
        email: String,
        now: Date,
        expired: Date
    ): String {
        return Jwts.builder()
            .setClaims(mapOf("type" to "JWT"))
            .setSubject(email)
            .setIssuedAt(now)
            .signWith(key)
            .setExpiration(expired)
            .compact()
    }
}

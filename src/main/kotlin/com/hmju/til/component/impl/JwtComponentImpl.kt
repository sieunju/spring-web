package com.hmju.til.component.impl

import com.hmju.til.component.JwtComponent
import com.hmju.til.core.exception.InvalidJwtException
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
 * Description :
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

    // 테스트 3분
    private val expiredTimeMs: Long by lazy {
        1000 * 60 * 10
    }

    override fun createToken(userEmail: String): String {
        val now = Date(System.currentTimeMillis())
        val expired = Date(System.currentTimeMillis().plus(expiredTimeMs))
        return Jwts.builder()
            .setClaims(mapOf("typ" to "JWT"))
            .setSubject(userEmail)
            .setIssuedAt(now)
            .signWith(key)
            .setExpiration(expired)
            .compact()
    }

    override fun getHeaderToken(
        req: HttpServletRequest
    ): String? {
        val auth = req.getHeader(HttpHeaders.AUTHORIZATION)
        if (auth.isNullOrEmpty()) return null
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
            val claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .body
            return !claims.expiration.before(Date())
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
}

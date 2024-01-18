package com.hmju.til.component.impl

import com.hmju.til.component.JwtComponent
import io.jsonwebtoken.Jwts
import jakarta.annotation.PostConstruct
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.SecretKey

/**
 * Description :
 *
 * Created by juhongmin on 1/16/24
 */
@Component
@Suppress("unused")
internal class JwtComponentImpl : JwtComponent {

    private val logger: Logger by lazy { LoggerFactory.getLogger(this.javaClass) }

    //    @Value("\${jwt.secret-key}")
//    private lateinit var secretKey: String
    private lateinit var key: SecretKey

    // 테스트 3분
    private val expiredTimeMs: Long by lazy {
        1000 * 60 * 10
    }

    @PostConstruct
    protected fun init() {
        key = Jwts.SIG.HS512.key().build()
    }

    override fun createToken(userEmail: String): String {
        val now = Date(System.currentTimeMillis())
        val expired = Date(System.currentTimeMillis().plus(expiredTimeMs))
        return Jwts.builder()
            .header()
            .add("typ", "JWT")
            .and()
            .subject(userEmail)
            .issuedAt(now)
            .expiration(expired)
            .signWith(key)
            .compact()
    }

    override fun getHeaderToken(
        req: HttpServletRequest
    ): String? {
        return req.getHeader("Authorization")
    }

    override fun isValidate(
        token: String
    ): Boolean {
        return try {
            val claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token).payload
            return !claims.expiration.before(Date())
        } catch (ex: Exception) {
            logger.info("ERROR $ex")
            false
        }
    }

    override fun getAuthentication(token: String): Authentication {
        val subject = Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .payload.subject
        return UsernamePasswordAuthenticationToken(
            subject,
            ""
        )
    }
}

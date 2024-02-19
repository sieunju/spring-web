package com.hmju.til.component.impl

import com.hmju.til.component.JwtComponent
import com.hmju.til.core.exception.ExpiredAuthException
import com.hmju.til.core.exception.InvalidAuthException
import com.hmju.til.features.auth_jwt.model.entity.JsonWebToken
import com.hmju.til.features.auth_jwt.model.entity.JwtInfo
import com.hmju.til.features.auth_jwt.model.vo.AuthVo
import com.hmju.til.toLocalDateTime
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.security.Key
import java.time.LocalDateTime
import java.util.Date

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
    private val refreshExpiredMinute = 30
    private val minuteTimeMs: Long by lazy { 60 * 1000 }

    /**
     * Create Json Web Token
     * @param vo RequestBody
     * @return JWT 에 대한 정보를 리턴하는 데이터 모델
     */
    override fun create(vo: AuthVo): JwtInfo {
        return JwtInfo.Builder()
            .setEmail(vo.email)
            .setToken(createTokenAndExpired(vo.email, vo.expiredMinute))
            .setRefreshToken(createTokenAndExpired(vo.email, refreshExpiredMinute))
            .build()
    }

    override fun create(entity: JsonWebToken): JwtInfo {
        return JwtInfo.Builder()
            .setEmail(entity.email)
            .setToken(createTokenAndExpired(entity.email, 5))
            .setRefreshToken(createTokenAndExpired(entity.email, refreshExpiredMinute))
            .build()
    }

    override fun isTokenValidate(auth: String): Boolean {
        return auth.startsWith("Bearer ")
    }

    override fun getHeaderToken(req: HttpServletRequest): String? {
        val auth = req.getHeader(AUTHORIZATION)
        if (auth.isNullOrEmpty()) return null
        if (!isTokenValidate(auth)) throw InvalidAuthException(auth)
        return getHeaderToken(auth)
    }

    override fun getHeaderToken(auth: String): String {
        // Bearer
        val originAuth =
            if (auth.startsWith("Bearer ")) {
                auth.substring(7)
            } else {
                auth
            }
        return originAuth
            .replace(Regex("[+/=]]")) {
                when (it.value) {
                    "+" -> "-"
                    "/" -> "_"
                    "=" -> ""
                    else -> it.value
                }
            }
    }

    override fun isValidate(token: String): Boolean {
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
            val claims =
                Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .body
            val tokenTime = claims.expiration.toLocalDateTime()
            tokenTime.isBefore(LocalDateTime.now())
        } catch (ex: ExpiredJwtException) {
            throw ExpiredAuthException(token)
        } catch (ex: Exception) {
            true
        }
    }

    override fun getAuthentication(token: String): Authentication {
        val claims =
            Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .body
//        val authorities: Collection<GrantedAuthority?> =
//            Arrays.stream(claims["Bearer"].toString().split(",".toRegex()).dropLastWhile { it.isEmpty() }
//                .toTypedArray())
//                .map { role: String? -> SimpleGrantedAuthority(role) }
//                .collect(Collectors.toList())
        // authorities 에 대해서 좀 알아봐야 할듯
        return UsernamePasswordAuthenticationToken(
            claims.subject,
            token,
            mutableListOf(),
        )
    }

    private fun createTokenAndExpired(
        email: String,
        expiredMinute: Int,
    ): Pair<String, Date> {
        val now = Date()
        val expired = Date(now.time.plus(expiredMinute * minuteTimeMs))
        val token =
            Jwts.builder()
                .setClaims(mapOf("type" to "JWT"))
                .setSubject(email)
                .setIssuedAt(now)
                .signWith(key)
                .setExpiration(expired)
                .compact()
        return token to expired
    }
}
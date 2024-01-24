package com.hmju.til

import com.hmju.til.component.JwtComponent
import com.hmju.til.features.auth_jwt.model.vo.AuthVo
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

/**
 * Description :
 *
 * Created by juhongmin on 1/17/24
 */
@SpringBootTest
class JwtTest {
    @Autowired
    lateinit var jwtComponent: JwtComponent

    @Test
    fun JWT_유효성() {
        val vo = AuthVo(email = "test@gmail.com")
        val entity = jwtComponent.createToken(vo)
        println("Token $entity")
        assert(jwtComponent.isValidate(entity.token))
        val auth = jwtComponent.getAuthentication(entity.token)
        println("AUTH $auth")
    }

    @Test
    fun JWT_기간만료시(){
        val vo = AuthVo(email = "test@gmail.com", expiredMinute = 1)
        val entity = jwtComponent.createToken(vo)
        println("Token ${entity.token}")
        val token = "eyJhbGciOiJIUzI1NiJ9.eyJ0eXBlIjoiSldUIiwic3ViIjoidGVzdEBnbWFpbC5jb20iLCJpYXQiOjE3MDU4NDE4ODMsImV4cCI6MTcwNTg0MTk0M30.AJnVuCvwE7Huz4cgXYliRunNpqNfID4S1NdLF-en7Z8"
            .replace("+", "-")
            .replace("/", "_")
            .replace("=", "")
        val validate= jwtComponent.isValidate(token)
        val expired = jwtComponent.isExpired(token)
        println("여기서 만든 코튼입니까? $validate  만료된 토큰 $expired")
    }
}
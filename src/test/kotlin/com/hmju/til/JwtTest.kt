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
}
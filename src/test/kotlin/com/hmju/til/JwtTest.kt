package com.hmju.til

import com.hmju.til.component.JwtComponent
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
    fun JWT_유효성(){
        val token = jwtComponent.createToken("test@gmail.com")
        println("Token $token")
        assert(jwtComponent.isValidate(token))
        val auth = jwtComponent.getAuthentication(token)
        println("AUTH $auth")
    }
}
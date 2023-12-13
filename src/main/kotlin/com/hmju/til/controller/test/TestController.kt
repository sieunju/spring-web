package com.hmju.til.controller.test

import com.hmju.til.model.TestModel
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/test")
class TestController {

    @GetMapping
    @ResponseBody
    fun fetch(): ResponseEntity<TestModel> {
        return ResponseEntity.ok(
            TestModel(
                id = System.currentTimeMillis(), message = "Hello it's me"
            )
        )
        // 아래 이거 왜 안됨?
//        return ResponseEntity<TestModel>(
//            status = HttpStatus.OK,
//            body = TestModel(id = System.currentTimeMillis(), message = "Hello it's me")
//        )
    }
}

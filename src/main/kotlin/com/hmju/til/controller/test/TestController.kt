package com.hmju.til.controller.test

import com.hmju.til.model.TestModel
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/test")
@Suppress("unused")
class TestController {

    @GetMapping
    @ResponseBody
    fun fetch(): ResponseEntity<TestModel> {
        return ResponseEntity<TestModel>(
            TestModel(id = System.currentTimeMillis(), message = "Hello it's me"),
            HttpStatus.OK
        )
    }
}

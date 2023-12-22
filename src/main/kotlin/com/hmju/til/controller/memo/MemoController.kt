package com.hmju.til.controller.memo

import com.hmju.til.model.dto.MemoDTO
import com.hmju.til.service.MemoServiceImpl
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Description : Memo Controller
 *
 * Created by juhongmin on 12/22/23
 */
@RestController
@RequestMapping("/api/v1/memo")
@Suppress("unused")
class MemoController @Autowired constructor(
    private val service: MemoServiceImpl
) {

    private val logger: Logger by lazy { LoggerFactory.getLogger(this.javaClass) }

    @GetMapping
    fun fetch(): ResponseEntity<List<MemoDTO>> {
        logger.debug("여길 탄다!@!@!@!@!@! ")
        return ResponseEntity<List<MemoDTO>>(
            service.fetch().map { MemoDTO(it) },
            HttpStatus.OK
        )
    }
}
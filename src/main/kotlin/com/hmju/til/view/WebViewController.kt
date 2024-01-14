package com.hmju.til.view

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

/**
 * Description : Memo View Controller
 *
 * Created by juhongmin on 1/14/24
 */
@Controller
@RequestMapping("/views")
@Suppress("unused")
class WebViewController {

    private val logger: Logger by lazy { LoggerFactory.getLogger(this.javaClass) }

    @GetMapping("/memo")
    fun memoPage() : String {
        // return "index.html"
        return "memo/index.html"
    }

    @GetMapping("/android")
    fun androidPage(): String {
        return "android/index.html"
    }
}

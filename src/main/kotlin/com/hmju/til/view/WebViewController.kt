package com.hmju.til.view

import com.hmju.til.features.android.AosMemoService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

/**
 * Description : Memo View Controller
 *
 * Created by juhongmin on 1/14/24
 */
@Controller
@Suppress("unused")
class WebViewController
    @Autowired
    constructor(
        private val aosMemoService: AosMemoService,
    ) {
        private val logger: Logger by lazy { LoggerFactory.getLogger(this.javaClass) }

        @GetMapping("/views/memo")
        fun memoPage(): String {
            return "memo/index.html"
        }

//    @GetMapping("/views/android")
//    fun androidPage(
//        response: HttpServletResponse
//    ) {
//        println("Android 메모 페이지 $response")
//        response.sendRedirect("android/index.html")
//        // return "android/index.html"
//    }
//
//    @GetMapping("/views/android/add")
//    fun androidAddPage(
//        response: HttpServletResponse
//    ) {
//        // response.sendRedirect("android/add.html")
//        response.sendRedirect("./android/add.html")
//    }
    }
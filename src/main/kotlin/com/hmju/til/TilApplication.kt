package com.hmju.til

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@SpringBootApplication
class TilApplication

fun main(args: Array<String>) {
    runApplication<TilApplication>(*args)
}
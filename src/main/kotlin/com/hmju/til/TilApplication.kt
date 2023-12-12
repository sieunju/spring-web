package com.hmju.til

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TilApplication

fun main(args: Array<String>) {
	runApplication<TilApplication>(*args)
}

package org.example.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringKotlinDemoApplication

fun main(args: Array<String>) {
    runApplication<SpringKotlinDemoApplication>(*args)
}

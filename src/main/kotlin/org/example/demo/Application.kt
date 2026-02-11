package org.example.demo

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}

@Configuration
@Import(DemoBeanRegistrar::class)
class DemoConfiguration


package com.altruist.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AltruistBackendApplication

fun main(args: Array<String>) {
    runApplication<AltruistBackendApplication>(*args)
}

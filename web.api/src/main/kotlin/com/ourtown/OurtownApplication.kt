package com.ourtown

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class OurtownApplication

fun main(args: Array<String>) {
    runApplication<OurtownApplication>(*args)
}

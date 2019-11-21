package com.study.webfluxtodokotlin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.reactive.config.EnableWebFlux

@SpringBootApplication
@EnableWebFlux
class WebfluxTodoKotlinApplication

fun main(args: Array<String>) {
    runApplication<WebfluxTodoKotlinApplication>(*args)
}

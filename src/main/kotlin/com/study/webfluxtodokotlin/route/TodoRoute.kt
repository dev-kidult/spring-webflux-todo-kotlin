package com.study.webfluxtodokotlin.route

import com.study.webfluxtodokotlin.handler.TodoHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.RouterFunctions.route

/**
 * @author Yonghui
 * @since 2019. 11. 20
 */

@Configuration
class TodoRoute(@Autowired val todoHandler: TodoHandler) {

    @Bean
    fun todoRouteFunction() = route()
            .path("api") { builder ->
                builder
                        .GET("/todos", todoHandler::getAll)
                        .GET("/todos/{id}", todoHandler::getById)
                        .POST("/todos", todoHandler::save)
                        .PUT("/todos/{id}/done", todoHandler::done)
                        .PUT("/todos/{id}/content", todoHandler::updateContent)
                        .DELETE("/todos/{id}", todoHandler::delete)
            }.build()
}
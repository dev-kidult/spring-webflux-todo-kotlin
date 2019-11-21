package com.study.webfluxtodokotlin.handler

import com.fasterxml.jackson.databind.ObjectMapper
import com.study.webfluxtodokotlin.domain.Todo
import com.study.webfluxtodokotlin.dto.TodoDto
import com.study.webfluxtodokotlin.repository.TodoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.validation.BeanPropertyBindingResult
import org.springframework.validation.Errors
import org.springframework.validation.Validator
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.ServerResponse.status
import org.springframework.web.server.ServerWebInputException
import reactor.core.publisher.Mono
import java.time.LocalDateTime

/**
 * @author Yonghui
 * @since 2019. 11. 20
 */

@Component
class TodoHandler(@Autowired val todoRepository: TodoRepository, @Autowired val validator: Validator, @Autowired val objectMapper: ObjectMapper) {
    fun getAll(serverRequest: ServerRequest): Mono<ServerResponse> {
        return ok().body(todoRepository.findAll(), Todo::class.java).log()
    }

    fun getById(serverRequest: ServerRequest): Mono<ServerResponse> {
        val id: Long = serverRequest.pathVariable("id").toLong()
        return ok().body(todoRepository.findById(id), Todo::class.java)
                .switchIfEmpty(status(HttpStatus.NOT_FOUND).build()).log()
    }

    fun save(serverRequest: ServerRequest): Mono<ServerResponse> {
        val todoDto: Mono<TodoDto> = serverRequest.bodyToMono(TodoDto::class.java).doOnNext(this::validate)
        return todoDto.flatMap {
            it.createdAt = LocalDateTime.now()
            ok().body(todoRepository.save(objectMapper.convertValue(it, Todo::class.java)), Todo::class.java)
        }.log()
    }

    fun done(serverRequest: ServerRequest): Mono<ServerResponse> {
        val id: Long = serverRequest.pathVariable("id").toLong()
        return todoRepository.findById(id)
                .flatMap {
                    it.done = true
                    it.updatedAt = LocalDateTime.now()
                    todoRepository.save(it)
                }
                .flatMap { ok().body(Mono.just(it), Todo::class.java) }
                .log()
    }

    fun updateContent(serverRequest: ServerRequest): Mono<ServerResponse> {
        val id: Long = serverRequest.pathVariable("id").toLong()
        val todoDto = serverRequest.bodyToMono(TodoDto::class.java).doOnNext(this::validate)
        return todoDto
                .flatMap {
                    todoRepository.findById(id).flatMap { todo ->
                        todo.content = it.content
                        todo.updatedAt = LocalDateTime.now()
                        todoRepository.save(todo)
                    }
                }
                .flatMap { ok().body(Mono.just(it), Todo::class.java) }
                .switchIfEmpty(status(HttpStatus.NOT_FOUND).build())
                .log()
    }

    fun delete(serverRequest: ServerRequest): Mono<ServerResponse> {
        val id: Long = serverRequest.pathVariable("id").toLong()
        return todoRepository.findById(id)
                .flatMap(todoRepository::delete)
                .flatMap { ok().build() }
                .switchIfEmpty(status(HttpStatus.NOT_FOUND).build())
                .log()
    }

    fun validate(any: Any) {
        val errors: Errors = BeanPropertyBindingResult(any, any::class.java.name)
        validator.validate(any, errors)
        if (errors.hasErrors()) {
            throw ServerWebInputException(errors.toString())
        }
    }
}

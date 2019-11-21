package com.study.webfluxtodokotlin.repository

import com.study.webfluxtodokotlin.domain.Todo
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository

/**
 * @author Yonghui
 * @since 2019. 11. 20
 */
@Repository
interface TodoRepository : ReactiveCrudRepository<Todo, Long>
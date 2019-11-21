package com.study.webfluxtodokotlin.domain

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

/**
 * @author Yonghui
 * @since 2019. 11. 20
 */

@Table
class Todo(
        @Id
        var id: Long? = null,
        @Column
        var content: String? = null,
        @Column
        var done: Boolean = false,
        @Column
        var createdAt: LocalDateTime = LocalDateTime.now(),
        @Column
        var updatedAt: LocalDateTime? = null
)
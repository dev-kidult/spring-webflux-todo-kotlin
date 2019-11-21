package com.study.webfluxtodokotlin.dto

import java.time.LocalDateTime
import javax.validation.constraints.NotBlank

/**
 * @author Yonghui
 * @since 2019. 11. 20
 */

data class TodoDto(var id: Long?, @field:NotBlank var content: String?, var createdAt: LocalDateTime?, var updatedAt: LocalDateTime?)
package com.study.webfluxtodokotlin.config

import io.r2dbc.h2.H2ConnectionConfiguration
import io.r2dbc.h2.H2ConnectionFactory
import io.r2dbc.spi.ConnectionFactory
import org.springframework.context.annotation.Configuration
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories

/**
 * @author Yonghui
 * @since 2019. 11. 20
 */

@Configuration
@EnableR2dbcRepositories
class R2dbcConfig : AbstractR2dbcConfiguration() {
    override fun connectionFactory(): ConnectionFactory {
        return H2ConnectionFactory(H2ConnectionConfiguration.builder().inMemory("todo").build())
    }
}
package com.sse.events.common

import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.test.annotation.DirtiesContext
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
@DirtiesContext
open class AbstractTestContainer {
    companion object {
        @Container
        @ServiceConnection
        val container =
            PostgreSQLContainer<Nothing>("postgres:latest").apply {
            withDatabaseName("test")
            withUsername("test")
            withPassword("test")
        }
    }

    @Test
    fun testContainer(){
        assertNotNull(container)
    }
}

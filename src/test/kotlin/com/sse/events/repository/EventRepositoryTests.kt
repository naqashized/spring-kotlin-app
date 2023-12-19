package com.sse.events.repository

import com.sse.events.domain.model.Event
import com.sse.events.domain.model.EventType
import com.sse.events.domain.repository.EventRepository
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.actuate.audit.AuditEventRepository
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import java.security.SecureRandom
import java.time.Instant
import java.time.temporal.ChronoUnit

@DataJpaTest(properties = ["spring.jpa.hibernate.ddl-auto=create"])
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class EventRepositoryTests {
    @Autowired
    private lateinit var eventRepository: EventRepository
    companion object {

        @Container
        @ServiceConnection
        val container = PostgreSQLContainer<Nothing>("postgres:latest").apply {
            withDatabaseName("test")
            withUsername("test")
            withPassword("test")
        }
    }

    @Test
    fun add(){
        val (event1, event2, event3) = triple()

        eventRepository.saveAll(listOf(event1, event2, event3))
    }

    private fun triple(): Triple<Event, Event, Event> {
        val event1 = Event(
            SecureRandom().nextLong(),
            EventType.IOT,
            "IOT event",
            Instant.now().minus(10, ChronoUnit.MINUTES)
        );
        val event2 = Event(
            SecureRandom().nextLong(),
            EventType.IOT,
            "Event 2",
            Instant.now().minus(4, ChronoUnit.MINUTES)
        )
        val event3 = Event(
            SecureRandom().nextLong(),
            EventType.REQUEST,
            "Event 2",
            Instant.now().minus(4, ChronoUnit.MINUTES)
        )
        return Triple(event1, event2, event3)
    }

    @Test
    fun shallFindAllAdded11MinsBack(){
        val (event1, event2, event3) = triple()

        eventRepository.saveAll(listOf(event1, event2, event3))

        var result = eventRepository.findAllByCreatedDateAfter(
            Instant.now().minus(11, ChronoUnit.MINUTES).truncatedTo(ChronoUnit.MINUTES)
        )
        assertTrue(result.size.equals(3))
    }

}
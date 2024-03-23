package com.sse.events.repository

import com.sse.events.common.AbstractTestContainer
import com.sse.events.domain.model.Event
import com.sse.events.domain.model.EventType
import com.sse.events.domain.repository.EventRepository
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import java.security.SecureRandom
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.temporal.ChronoUnit

@DataJpaTest(properties = ["spring.jpa.hibernate.ddl-auto=create"])
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class EventRepositoryTests:  AbstractTestContainer() {
    @Autowired
    private lateinit var eventRepository: EventRepository
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

    val  Event.getDate: LocalDateTime
        get() = LocalDateTime.ofInstant(createdDate, ZoneId.systemDefault())


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

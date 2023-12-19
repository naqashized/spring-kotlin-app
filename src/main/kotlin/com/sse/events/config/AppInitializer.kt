package com.sse.events.config

import com.sse.events.domain.model.Event
import com.sse.events.domain.model.EventType
import com.sse.events.domain.repository.EventRepository
import jakarta.annotation.PostConstruct
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import java.security.SecureRandom
import java.time.Instant

@Component
@Profile("!test")
class AppInitializer(private val eventRepository: EventRepository) {
    private val logger = LoggerFactory.getLogger(javaClass)
    @PostConstruct
    fun initialize(){
        logger.info("Adding data to events")
        val even1 = Event(SecureRandom().nextLong(), EventType.REQUEST,"Request Test", Instant.now());
        val even2 = Event(SecureRandom().nextLong(), EventType.IOT,"Test2", Instant.now());
        eventRepository.saveAll(listOf(even1,even2))
    }

}
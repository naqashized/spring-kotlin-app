package com.sse.events.service

import com.sse.events.common.AbstractTestContainer
import com.sse.events.domain.model.EventType
import com.sse.events.domain.service.EventService
import com.sse.events.dto.EventRequest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import java.time.Instant
import java.time.temporal.ChronoUnit

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = [ "spring.jpa.hibernate.ddl-auto=create" ]
)
@ActiveProfiles("test")
class EventServiceTests:  AbstractTestContainer() {
    @Autowired
    private lateinit var eventService: EventService

    @Test
    fun add() {
        //Arrange
        val eventRequest = EventRequest(EventType.IOT, "Data being sent")
        //Act
        val newEventResponse = eventService.add(eventRequest)
        //Assert
        Assertions.assertNotNull(newEventResponse)
        Assertions.assertTrue(newEventResponse.id > 0)
    }

    @Test
    fun findAllByDate() {
        //Arrange
        val eventRequest = EventRequest(EventType.IOT, "Data being sent")
        val eventRequest2 = EventRequest(EventType.IOT, "New Event")
        eventService.add(eventRequest)
        eventService.add(eventRequest2)
        //Act
        var time = Instant.now().truncatedTo(ChronoUnit.MINUTES);
        var response = eventService.findAllByCreatedDate(time);
        //Assert
        Assertions.assertNotNull(response)
    }

}

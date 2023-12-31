package com.sse.events.service

import com.sse.events.domain.model.EventType
import com.sse.events.domain.service.EventService
import com.sse.events.dto.EventRequest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import java.time.Instant
import java.time.temporal.ChronoUnit
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;

//@DataJpaTest
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = [ "spring.jpa.hibernate.ddl-auto=create" ]
)
@ActiveProfiles("test")
class EventServiceTests {
    @Autowired
    private lateinit var eventService: EventService

    companion object {

        @Container
        @ServiceConnection
        val postgreSQLContainer = PostgreSQLContainer<Nothing>("postgres:latest").apply {
            withDatabaseName("test")
            withUsername("test")
            withPassword("test")
        }
    }



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
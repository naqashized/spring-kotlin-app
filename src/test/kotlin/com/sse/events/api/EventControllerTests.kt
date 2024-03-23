package com.sse.events.api

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.sse.events.common.AbstractTestContainer
import com.sse.events.domain.model.Event
import com.sse.events.domain.model.EventType
import com.sse.events.domain.repository.EventRepository
import com.sse.events.dto.EventRequest
import com.sse.events.dto.EventResponse
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import java.nio.charset.StandardCharsets
import java.security.SecureRandom
import java.time.Instant
import java.time.temporal.ChronoUnit

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
                properties = [ "spring.jpa.hibernate.ddl-auto=create" ]
)
@AutoConfigureMockMvc
class EventControllerTests @Autowired constructor(
    val mockMvc: MockMvc,
    val objectMapper: ObjectMapper,
    val eventRepository: EventRepository
):  AbstractTestContainer() {

    @BeforeEach
    fun setup(){
        eventRepository.deleteAll()
    }

    @Test
    fun `should create an event`() {
        // Arrange
        val eventRequest = EventRequest(EventType.REQUEST, "Running test")
        // Act/Assert
        mockMvc.post("/events") {
            contentType = mediaType()
            content = objectMapper.writeValueAsString(eventRequest)
        }
                .andDo { print() }
                .andExpect { status { isOk() } }
                .andExpect { jsonPath("$.id"){isNumber()}}
    }

    @Test
    fun `should find all events`() {
        val event = Event(
            SecureRandom().nextLong(),
            EventType.IOT,
            "IOT event",
            Instant.now().minus(10, ChronoUnit.MINUTES)
        )
        eventRepository.save(event)
        val expected =
            EventResponse(event.id, event.message, event.type, event.createdDate)


        val result = mockMvc.get("/events")
                .andDo { print() }
                .andExpect { status { isOk() } }
                .andExpect { jsonPath("$"){isArray()}}
                .andReturn()
        val response =
            objectMapper.readValue(
                result.response.contentAsString,
                object : TypeReference<List<EventResponse>>() {}
            )
        assertThat(response[0])
                .usingRecursiveComparison()
                .ignoringFields("createDate")
                .isEqualTo(expected);
    }

    @Test
    fun `should find event by id`() {
        val event = Event(
            SecureRandom().nextLong(),
            EventType.IOT,
            "IOT event",
            Instant.now().minus(10, ChronoUnit.MINUTES)
        )
        eventRepository.save(event)
        val expected =
            EventResponse(event.id, event.message, event.type, event.createdDate)


        val result = mockMvc.get("/events/{id}",event.id)
                .andDo { print() }
                .andExpect { status { isOk() } }
                .andReturn()
        val response =
            objectMapper.readValue(
                result.response.contentAsString,
                EventResponse::class.java
            )
        assertThat(response)
                .usingRecursiveComparison()
                .ignoringFields("createDate")
                .isEqualTo(expected);
    }

    private fun mediaType(): MediaType {
        return MediaType(
            "application",
            "json",
            StandardCharsets.UTF_8
        )
    }
}

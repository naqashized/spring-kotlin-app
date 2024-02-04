package com.sse.events.domain.service

import com.sse.events.domain.model.Event
import com.sse.events.domain.repository.EventRepository
import com.sse.events.dto.EventRequest
import com.sse.events.dto.EventResponse
import com.sse.events.dto.NewEventResponse
import org.springframework.stereotype.Service
import java.security.SecureRandom
import java.time.Instant

@Service
class EventService(val eventRepository: EventRepository) {

    fun findAll():List<EventResponse>{
        return eventRepository.findAll()
            .stream()
            .map { event ->
                EventResponse(event.id, event.message, event.type, event.createdDate)
            }
            .toList()
    }

    fun add(eventRequest: EventRequest): NewEventResponse {
        var newEvent = eventRepository.save(
            Event(
                SecureRandom().nextLong(),
                eventRequest.type,
                eventRequest.message,
                Instant.now()
            )
        )
        return NewEventResponse(newEvent.id);
    }

    fun update(id:Long, eventRequest: EventRequest): EventResponse {
        var event = eventRepository.findById(id)
            .map { event -> Event(event.id, eventRequest.type, eventRequest.message, event.createdDate) }
            .get()
        var updatedEvent = eventRepository.save(event);
        return EventResponse(updatedEvent.id, updatedEvent.message, updatedEvent.type, updatedEvent.createdDate)
    }

    fun findById(id: Long): EventResponse{
        return eventRepository.findById(id)
            .map { event -> EventResponse(event.id, event.message, event.type, event.createdDate) }
            .orElse(null)
    }

    fun findAllByCreatedDate(date: Instant): List<EventResponse>{
        return eventRepository.findAllByCreatedDateBefore(date)
            .stream()
            .map { event -> EventResponse(event.id, event.message, event.type, event.createdDate) }
            .toList();
    }
}



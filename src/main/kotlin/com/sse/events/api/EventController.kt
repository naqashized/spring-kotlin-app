package com.sse.events.api

import com.sse.events.domain.service.EventService
import com.sse.events.dto.EventRequest
import com.sse.events.dto.EventResponse
import com.sse.events.dto.NewEventResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping

@RestController
@RequestMapping("/events")
//@CrossOrigin("http://localhost:3000/")
class EventController(private val eventService: EventService) {

    @GetMapping
    fun findAll():List<EventResponse> {
        return eventService.findAll();
    }

    @PostMapping
    fun add(@RequestBody eventRequest: EventRequest): ResponseEntity<NewEventResponse>{
        return ResponseEntity.ok(eventService.add(eventRequest));
    }

    @PutMapping("/{id}")
    fun update(
        @RequestBody eventRequest: EventRequest,
        @PathVariable id: Long
    ): ResponseEntity<EventResponse>{
        return ResponseEntity.ok(eventService.update(id, eventRequest));
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): ResponseEntity<EventResponse>{
        return ResponseEntity.ok(eventService.findById(id));
    }
}

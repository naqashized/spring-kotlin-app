package com.sse.events.domain.repository

import com.sse.events.domain.model.Event
import org.springframework.data.jpa.repository.JpaRepository

interface EventRepository: JpaRepository<Event, Long> {
}
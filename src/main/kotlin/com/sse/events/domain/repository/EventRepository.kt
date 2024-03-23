package com.sse.events.domain.repository

import com.sse.events.domain.model.Event
import org.springframework.data.jpa.repository.JpaRepository
import java.time.Instant

interface EventRepository: JpaRepository<Event, Long> {
    fun findAllByCreatedDateBefore(createDate: Instant): List<Event>
    fun findAllByCreatedDateAfter(createDate: Instant):List<Event>
}

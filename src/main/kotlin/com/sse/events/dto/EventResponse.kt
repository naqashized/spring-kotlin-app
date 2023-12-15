package com.sse.events.dto

import com.sse.events.domain.model.EventType
import java.time.Instant

data class EventResponse(
    val id: Long,
    val message: String,
    val type: EventType,
    val createDate: Instant
)

package com.sse.events.domain.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import java.time.Instant

@Entity
class Event(
    @Id @GeneratedValue
    var id: Long,
    var type:EventType,
    var message: String,
    var createdDate: Instant
)

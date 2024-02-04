package com.sse.events.dto

import com.sse.events.domain.model.EventType

data class EventRequest(
    val type: EventType,
    val message: String
){
    override fun toString(): String {
        return "EventRequest(type=$type, message='$message')"
    }
}

package com.sse.events.api

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.time.LocalTime
import java.util.concurrent.Executors

@RestController
@RequestMapping("/sse")
class SSEventController {

    @GetMapping("")
    fun streamSseMvc(): SseEmitter? {
        val emitter = SseEmitter()
        val sseMvcExecutor = Executors.newSingleThreadExecutor()
        sseMvcExecutor.execute {
            try {
                var i = 0
                while (true) {
                    val event = SseEmitter.event()
                        .data("SSE Event:- " + LocalTime.now().toString())
                        .id(i.toString())
                        .name("sse event")
                    emitter.send(event)
                    Thread.sleep(1000)
                    i++
                }
            } catch (ex: Exception) {
                emitter.completeWithError(ex)
            }
        }
        return emitter
    }
}
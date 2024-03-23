package com.sse.events

import com.sse.events.api.EventController
import com.sse.events.common.AbstractTestContainer
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
				properties = [ "spring.jpa.hibernate.ddl-auto=create" ]
)
class EventsApplicationTests: AbstractTestContainer() {
	@Autowired
	lateinit var eventController: EventController
	@Test
	fun contextLoads() {
		assertNotNull(eventController)
	}

}

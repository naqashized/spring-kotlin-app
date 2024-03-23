import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.2.0"
	id("io.spring.dependency-management") version "1.1.0"
	kotlin("jvm") version "1.8.20"
	kotlin("plugin.spring") version "1.8.20"
	kotlin("plugin.jpa") version "1.8.20"
	id("io.gitlab.arturbosch.detekt") version "1.21.0"
}

group = "com.sse"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0")
	implementation ("org.springframework.boot:spring-boot-starter-actuator")
	runtimeOnly ("io.micrometer:micrometer-registry-prometheus")

	runtimeOnly("org.postgresql:postgresql")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.boot:spring-boot-testcontainers")
	testImplementation ("org.testcontainers:postgresql:1.16.3")
	testImplementation ("org.testcontainers:testcontainers")
	testImplementation ("org.testcontainers:junit-jupiter:1.16.3")
	testImplementation ("org.assertj:assertj-core:3.24.2")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

detekt {
	version = "1.21.0"
	config = files("config/detekt/detekt.yml")
	buildUponDefaultConfig = true
}

tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
	reports {
		xml.required.set(true)
		html.required.set(true)
		txt.required.set(true)
		sarif.required.set(true)
		md.required.set(true)
	}
}

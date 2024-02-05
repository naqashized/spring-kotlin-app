# spring-kotlin-app
This is a simple Kotlin app with Spring Web which allows us to create REST APIs. It uses PostgreSql database on the backend side for persistence.
## Server Sent Events (SSE)
This app provides /stream API which divides the large file to bytes and then publishes bytes of stream one by one.

## PostgreSql TestContainers
It has unit tests for data layer- repositories. It has integration tests for APIs.

## Running the app locally
There is docker compose file which can be used to run Postgres database.
Run the Postgres Container using docker compose command;

```shell script
docker-compose up -d
```

Building the application:

```shell script
./gradlew build
```
Run the app using below command, the app expects to run local profile:

```shell script
 ./gradlew bootRun --args=--spring.profiles.active=local
```

### API Documentation
Swagger API docs can be accessed below;

http://localhost:8080/swagger-ui/index.html
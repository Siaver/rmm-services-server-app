# RMM Service Implementation

## Built With

- [Spring Boot](https://projects.spring.io/spring-boot/) - The web framework used
- [Gradle](http://www.gradle.org/) - Build Tool

## Requirements
- Java runtime version 1.8 or later
- PostgreSQL database version 9.x

## Running / Testing

- `./gradlew bootRun` - runs app
- `./gradlew clean test` - runs tests

### Setup PostgresQL Database.
The default configuration is the following:
- Server: 127.0.0.1:5432
- Database Name: demo
- Credentials: test::!secure4test

### Security

The application uses basic authentication. The default user has the following credentials
 - username: "user" 
 - password: "test"

## API Documentation

The API uses swagger for documentation. It can be found running the app and going to `http://localhost:8080/swagger-ui.html#/` endpoint.

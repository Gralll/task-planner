# Test application: Task planner

It allows to work with user and task entities.
The project to test custom security and Swagger.

# Build

### Gradle
1)  To build the project you can use local gradle:
    - Execute `gradle clean build`.
2)  To build the project with wrapper:
    - Execute `gradlew clean build`.

# Run
### Gradle
1)  To run the project with local gradle:
    - Execute `gradle bootRun`.
2)  To run the project by wrapper:
    - Execute `gradlew bootRun`.
### Swagger
By default swagger for the application is available on:
`host:port/swagger-ui.html`.

For example on local machine: `http://localhost:8080/swagger-ui.html`
### H2 database
By default H2 for the application is available on:
http://localhost:8080/h2-console

###Authorization
Application uses token authorization process.
First, call /authenticate endpoint with credentials.
Second, call needed endpoint whicj required token passing it into
header with prefix "Bearer"

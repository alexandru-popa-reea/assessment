#NIA - Patient Surveys - code assesment
### This is just an example of how I/we would approach implementing an API for handling patients and surveys

##Techologies:

- Java 11
- Spring Boot 2.6
- MySQL 8.0.26 (as relational database)
- swagger / springfox (3.0) for generating API documentation
- Flyway 8.0.4 for database migrations
- Testcontainers for testing the data layer
- Junit 5 for testing
- JaCoCo for test coverage reporting

## Unit Testing and Integration Testing

- a special attention was paid for testing all layers: controller, service, data
- I have commited (just for convenience, test reports here)
    - Tests Summary
  ![Test Summary](jacoco/images/Tests.jpg?raw=true "Test Summary")
    - Tests Coverage
  ![Test Coverage](jacoco/images/TestCoverage.jpg?raw=true "Test Coverage")
- code coverage is not very good, but this was just an exercise.... in a real world scenario we would aim to 100% code coverage
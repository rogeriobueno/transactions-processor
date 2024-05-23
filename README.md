# Transaction Processor

Transaction Processor is a Java application that processes financial transactions from XML files.

## Description

This application reads XML files of financial transactions, processes the transactions, and saves them in a database.
The transactions include information such as the transaction value, currency, recipient, and credit card details.

The application also logs the processing time for each file and provides a processing summary.

## System Requirements

* Java (minimum version 21)
* Maven
* Docker
* MySQL (from Docker)

## Build

To build the project, run the following command:

#### Build normal image

```
mvn clean package
```

#### Build native image

```
mvn clean -pNative spring-boot:build-image
```

#### Docker build & up

[Compose](./docker/docker-compose.yml)

```
docker compose up --build
```

## API Documentation

The API documentation is available through Swagger UI, which can be accessed when the application is running.

http://localhost:8080/swagger-ui/index.html

## Next Steps (Improvements)

* Improve the performance of transaction processing.
* Add more tests to ensure code quality.
* Improve de File Storage Service to support better control and monitoring of files.
* Separate the API from File Processor to improve scalability (CQRS).
* Add cryptography to protect sensitive data.

## Author

- [@rogeriobueno](https://github.com/rogeriobueno)
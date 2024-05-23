# Build stage with mavem and openjdk
FROM maven:3.8.3-openjdk-17-slim AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn package

# Second stage with JRE and the built artifact
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/target/transaction-processor*.jar application.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "application.jar"]
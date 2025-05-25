# Build stage: Maven with Java 17 (latest official Maven image)
FROM maven:3.9.9-amazoncorretto-21-debian-bookworm AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Run stage: Use Eclipse Temurin 21 JDK for runtime
FROM eclipse-temurin:21-jdk
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]

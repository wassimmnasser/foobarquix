# Use lightweight Java 17 image
FROM eclipse-temurin:17-jdk-alpine

# Create working directory inside container
WORKDIR /app

# Copy generated jar file
COPY target/foobarquix-service-0.0.1-SNAPSHOT.jar app.jar

# Expose default Spring Boot port
EXPOSE 8080

# Run application
ENTRYPOINT ["java","-jar","/app.jar"]
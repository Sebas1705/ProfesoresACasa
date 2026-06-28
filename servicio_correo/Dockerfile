# Stage 1: Build the application with Maven
FROM maven:3.8.4-openjdk-11-slim AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Run the application
FROM openjdk:11-jdk-slim
WORKDIR /app
COPY --from=build /app/target/servicio_correo-1.0.jar /app/servicio_correo-1.0.jar
CMD ["java", "-jar", "servicio_correo-1.0.jar"]

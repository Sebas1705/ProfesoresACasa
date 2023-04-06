FROM openjdk:11-jre-slim
WORKDIR /app
COPY target/servicio_correo-1.0.jar /app
ENTRYPOINT ["java", "-jar", "servicio_correo-1.0.jar"]
FROM openjdk:11-jre-slim
WORKDIR /app
COPY target/profesores_a_casa-1.0.jar /app
EXPOSE 8443
ENTRYPOINT ["java", "-jar", "profesores_a_casa-1.0.jar"]
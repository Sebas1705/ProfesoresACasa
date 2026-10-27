# Stage 1: Build the application with Maven
FROM maven:3.8.4-openjdk-11-slim AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Run the application
FROM openjdk:11-jdk-slim
WORKDIR /app
COPY --from=build /app/target/profesores_a_casa-1.0.jar /app/profesores_a_casa-1.0.jar
EXPOSE 8443
# The HTTPS keystore is not part of the image. It is read from /app/certs (mounted by
# docker-compose); if none is there yet, a self-signed one is generated with the password
# from SSL_KEYSTORE_PASSWORD, so both web replicas share the same certificate.
ENV SSL_KEYSTORE=file:/app/certs/keystore.jks
CMD ["sh", "-c", "mkdir -p /app/certs && { [ -f /app/certs/keystore.jks ] || keytool -genkeypair -alias profesoresacasa -keyalg RSA -keysize 2048 -validity 365 -dname CN=localhost -storetype JKS -keystore /app/certs/keystore.jks -storepass \"$SSL_KEYSTORE_PASSWORD\" -keypass \"$SSL_KEYSTORE_PASSWORD\"; } && exec java -jar profesores_a_casa-1.0.jar"]

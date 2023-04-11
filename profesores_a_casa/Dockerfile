# Imagen base de Maven con Java 11
FROM maven:3.8.4-openjdk-11-slim AS build

# Copiar los archivos de la aplicación al contenedor
WORKDIR /app
COPY pom.xml .
COPY src ./src

# Compilar y empaquetar la aplicación en un archivo JAR
RUN mvn clean package -DskipTests

# Imagen base de Java 11
FROM openjdk:11-jdk-slim

# Directorio de trabajo en la imagen
WORKDIR /app

# Copiar el archivo JAR generado por la aplicación Spring Boot al contenedor
COPY --from=build /app/target/profesores_a_casa-1.0.jar /app/profesores_a_casa-1.0.jar

# Exponer el puerto 8443
EXPOSE 8443

# Comando para ejecutar la aplicación Spring Boot
CMD ["java", "-jar", "profesores_a_casa-1.0.jar"]
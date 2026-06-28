# TutorsAtHome (ProfesoresACasa)

[![Demo Video](https://github.com/Sebas1705/ProfesoresACasa/blob/955cb624999d4453fc0e3773a787c494e9d5357c/assets/Miniatura.png)](https://www.youtube.com/watch?v=qjc6L4i1CjM)

A distributed web application built for the **Distributed Applications Development** course at Universidad Rey Juan Carlos. The platform connects private tutors who publish lesson offers with students looking to hire them. Users can act as both tutor and student simultaneously.

## Features

### Public (unauthenticated)
- Browse tutor posts with pagination
- View tutor profiles and their offers
- Register a new account

### Private (authenticated)
- Publish and manage your own lesson offers
- Book a tutor by creating a contract
- Manage your contracts (as teacher or student)
- Report posts for policy violations
- Rate posts (1–5 stars)
- Edit your profile name and bio
- Delete your account

## Data Model

| Entity | Description |
|--------|-------------|
| `User` | Platform account — can act as both tutor and student |
| `Post` | A tutor's lesson offer with title, description, price, and rating |
| `Contract` | A hiring agreement between a student and a tutor for a specific post |
| `Report` | A policy violation report on a post |
| `Ranking` | Running average star rating for a post |

## Architecture

```
┌───────────────────────────────────────────────────────────┐
│                      HAProxy (port 443)                   │
│                    Round-robin load balancer               │
└──────────────────┬────────────────────┬──────────────────┘
                   │                    │
          ┌────────▼───────┐   ┌────────▼───────┐
          │  web1 (:8443)  │   │  web2 (:8443)  │
          │  Spring Boot   │   │  Spring Boot   │
          │  + Hazelcast   │◄──►  + Hazelcast   │
          └────────┬───────┘   └────────┬───────┘
                   │                    │
       ┌───────────▼────────────────────▼──────────┐
       │               MySQL 8                      │
       └────────────────────────────────────────────┘
                   │
          ┌────────▼───────┐
          │    RabbitMQ    │
          └────────┬───────┘
                   │
          ┌────────▼───────┐
          │  email-service │  (consumes queue, sends Gmail notifications)
          └────────────────┘
```

**Key technologies:** Spring Boot 2.7, Spring Security, Spring Data JPA, Mustache templates, Hazelcast distributed sessions, RabbitMQ (AMQP), MySQL 8, HAProxy, Docker Compose.

## Notification Events

The email microservice (`servicio_correo`) listens on a RabbitMQ queue and sends Gmail notifications for:

| Code | Event |
|------|-------|
| `S`  | User registration |
| `I`  | New post published |
| `P`  | Post deleted |
| `N`  | New contract created |
| `C`  | Contract cancelled |
| `R`  | Report submitted |
| `D`  | Account deleted |

## Architecture Diagrams

### UML — Main app (ProfesoresACasa)
![UML](https://github.com/Sebas1705/ProfesoresACasa/blob/36033e31ac6c0ef8987258aa91a1566acb89fa97/assets/DiagramaClasesYTemplates.png)

> Legend: Orange = Controller · Green = Template · Purple = Repository · Blue = Model · Violet = Config · Yellow = Service

### UML — Email service (ServicioCorreo)
![Rabbit](https://github.com/Sebas1705/ProfesoresACasa/blob/0b1e814538304a8e1ddf9b920971147571b7668d/assets/DiagramaClasesYTemplates2.png)

### Navigation diagram
![Navigation](https://github.com/Sebas1705/ProfesoresACasa/blob/0255de7a38de9a10f00ed54e1423925ff469cf63/assets/navegacion.png)

### Infrastructure
![Infrastructure](https://github.com/Sebas1705/ProfesoresACasa/blob/ab5b5dbe4a01ca5e9c8f254df7f9e79fb9ad7194/assets/Infraestructura.png)

## Running with Docker Compose

### Prerequisites
- Docker and Docker Compose installed
- A Gmail account with an [App Password](https://support.google.com/accounts/answer/185833) configured

### Configuration

Before running, set the required environment variables. Either edit `docker-compose.yml` or export them in your shell:

```bash
export GMAIL_ADDRESS=your.email@gmail.com
export GMAIL_APP_PASSWORD=your_16_char_app_password
```

> ⚠️ **Never commit real credentials to source control.** The `docker-compose.yml` in this repo uses placeholder values — replace them with your own before running.

### Start

```bash
docker compose up --build
```

Access the app at **https://localhost:9443** (self-signed certificate — accept the browser warning).

## Manual Setup (without Docker)

### 1. Install prerequisites

```bash
sudo apt install default-jre mysql-server rabbitmq-server
```

### 2. Configure MySQL

```bash
sudo mysql
```
```sql
CREATE USER 'test'@'localhost' IDENTIFIED BY 'test.profesoresACasa';
GRANT ALL PRIVILEGES ON *.* TO 'test'@'localhost' WITH GRANT OPTION;
CREATE SCHEMA profesoresACasa;
exit
```

### 3. Build the JARs

```bash
cd profesores_a_casa && mvn clean package -DskipTests && cd ..
cd servicio_correo   && mvn clean package -DskipTests && cd ..
```

### 4. Run both services (in separate terminals)

```bash
java -jar profesores_a_casa/target/profesores_a_casa-1.0.jar
java -jar servicio_correo/target/servicio_correo-1.0.jar
```

Browse to **https://localhost:8443** once both services are running.

## Screenshots

| Home | Login | Sign Up |
|------|-------|---------|
| ![Home](https://github.com/Sebas1705/ProfesoresACasa/blob/51c43629d4d50a709ad37b0c0c42cfbfbc075981/Navegacion/Home.png) | ![Login](https://github.com/Sebas1705/ProfesoresACasa/blob/de766cbca664a1e8a9287ce5fb02c464d2c545d4/Navegacion/Login.png) | ![SignUp](https://github.com/Sebas1705/ProfesoresACasa/blob/51c43629d4d50a709ad37b0c0c42cfbfbc075981/Navegacion/Registro.png) |

| My Profile | Post Detail | Book a Tutor |
|------------|-------------|--------------|
| ![Profile](https://github.com/Sebas1705/ProfesoresACasa/blob/51c43629d4d50a709ad37b0c0c42cfbfbc075981/Navegacion/Resgistrado.png) | ![Post](https://github.com/Sebas1705/ProfesoresACasa/blob/51c43629d4d50a709ad37b0c0c42cfbfbc075981/Navegacion/Ver_Post.png) | ![Contract](https://github.com/Sebas1705/ProfesoresACasa/blob/51c43629d4d50a709ad37b0c0c42cfbfbc075981/Navegacion/Contratar.png) |

## Authors

- [Sebas1705](https://github.com/Sebas1705)
- [AHoyasR](https://github.com/AHoyasR)

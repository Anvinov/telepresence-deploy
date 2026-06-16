# Synexis Deployment Repository

Este repositorio contiene la solución de despliegue para una plataforma de servicios basada en Java y Spring Boot, con soporte de gestión, streaming y autenticación/autoría a través de Keycloak.

## Estructura del repositorio

- `backend-management-service/`
  - Servicio principal de gestión.
  - Basado en Spring Boot 4 y Java 21.
  - Incluye autenticación OAuth2/Keycloak, JPA, Flyway, H2 y PostgreSQL.
  - Expone API REST para operaciones de administración, usuarios, pagos y seguridad.

- `streaming-service/`
  - Servicio de transmisión en tiempo real.
  - Basado en Spring Boot 4 y Java 21.
  - Incluye WebSocket, seguridad y JWT para transporte seguro de eventos.

- `keycloak/`
  - Configuración de Keycloak para autenticación y autorización.
  - Incluye exportación de realm y Dockerfile para despliegue local.

## Tecnologías principales

- Java 21
- Spring Boot 4
- Spring Security
- Spring WebMVC
- Spring WebFlux
- Spring Data JPA
- Keycloak
- WebSocket
- Flyway
- PostgreSQL con extención PostGis
- Lombok

## Requisitos previos

- Java 21
- Maven
- Docker (para ejecutar `keycloak` y otros contenedores)

## Cómo ejecutar

1. Levantar el servicio Keycloak (opcional según el despliegue):
   - `cd keycloak`
   - `docker build -t synexis-keycloak .`
   - `docker run --name synexis-keycloak -p 8085:8080 synexis-keycloak`

2. Ejecutar el backend de gestión:
   - `cd backend-management-service`
   - `./mvnw spring-boot:run`

3. Ejecutar el servicio de streaming:
   - `cd streaming-service`
   - `./mvnw spring-boot:run`

## Notas

- El backend de gestión está diseñado para integrarse con Keycloak y puede usar PostgreSQL en producción.
- El servicio de streaming expone endpoints WebSocket para eventos en tiempo real.
- Ajustar la configuración en `application.yaml` de cada módulo según el entorno.

## Contacto

Para más detalles o mejoras, revisar el código en cada servicio y actualizar los archivos de configuración según el entorno de despliegue.

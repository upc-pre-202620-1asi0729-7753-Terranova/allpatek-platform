# Allpatek Platform

[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](https://opensource.org/licenses/MIT)

REST API de Allpatek para gestionar parcelas, contratos, bóveda de pagos, evidencias de hitos y alertas climáticas.

Los bounded contexts viven uno al lado del otro bajo `com.allpatekplatform`: `shared`, `iam`, `parcels`, `agreements`, `escrow`, `tracking` y `alerts`. El perfil está en `shared`. `iam` queda como carpeta vacía, sin código todavía.

## Features

- **`/api/v1/parcels`**: crear, actualizar, listar y eliminar parcelas (`parcels`).
- **`/api/v1/agreements`**: crear y actualizar contratos entre agricultor y comerciante (`agreements`).
- **`/api/v1/wallets`**: registrar y consultar la tarjeta/bóveda del perfil (`escrow`).
- **`/api/v1/evidence`**: subir y revisar evidencia de hitos de campaña (`tracking`).
- **`/api/v1/alerts`**: alertas climáticas a partir de las coordenadas de la parcela y Open-Meteo (`alerts`).
- **`/api/v1/profiles`**: consultar y actualizar el perfil (`shared`).
- **`/health`**: health check.
- **Swagger UI**: `http://localhost:8090/swagger-ui/index.html`.

## Technologies

- Java 27 and Spring Boot 4
- Apache Maven (or the bundled `./mvnw` wrapper)
- Spring Web (REST controllers)
- Spring Data JPA (Hibernate) with MySQL
- Spring Validation (Jakarta Bean Validation)
- SpringDoc OpenAPI 3 / Swagger UI
- Lombok

## Prerequisites

- Java 27 or higher
- Maven (or the bundled `./mvnw` wrapper)
- MySQL 8+ running locally

## Environment variables

| Variable | Description | Default (dev) |
|---|---|---|
| `SPRING_PROFILES_ACTIVE` | Active Spring profile | `dev` |
| `DATABASE_USER` | MySQL username | `root` |
| `DATABASE_PASSWORD` | MySQL password | *(empty, local XAMPP)* |
| `DATABASE_URL` | MySQL host | `localhost` |
| `DATABASE_PORT` | MySQL port | `3306` |
| `DATABASE_NAME` | Database name | `allpatek-os` |
| `PORT` | Application port | `8090` |

## Spring profiles

| Profile | Usage |
|---|---|
| `dev` | Local defaults; `ddl-auto=update`; SQL logging on; port `8090` |
| `prod` | Variables required from the environment; `ddl-auto=validate`; SQL logging off |

## Architecture notes

Each bounded context uses this layout:

```
application/commandservices|queryservices
application/internal/commandservices|queryservices
domain/model/{aggregates,commands,queries,valueobjects}
domain/repositories
infrastructure/persistence/jpa/{adapters,assemblers,converters,embeddables,entities,repositories}
interfaces/rest/{resources,transform}
interfaces/acl
```

- `shared.application.result.Result` and `shared.application.result.ApplicationError` model command outcomes.
- Command and query services are separate contracts (CQRS).
- Aggregates have no JPA annotations; persistence lives in `*PersistenceEntity` / `*PersistenceAssembler` / `*RepositoryImpl`.
- `alerts` reads parcel coordinates through `ParcelsContextFacade`, never `parcels.domain`.
- i18n uses `messages` and `ValidationMessages` (`en` / `es`) via `Accept-Language`.

## Error responses

- Validation and domain input errors return `400 Bad Request`.
- A conflicting or duplicate resource returns `409 Conflict`.
- A business rule violation returns `422 Unprocessable Entity`.
- Not-found lookups return `404 Not Found`.
- Unexpected errors return `500 Internal Server Error`.

## Run

```bash
./mvnw spring-boot:run
```

- API: `http://localhost:8090/api/v1`
- Swagger: `http://localhost:8090/swagger-ui/index.html`
- Health: `http://localhost:8090/health`

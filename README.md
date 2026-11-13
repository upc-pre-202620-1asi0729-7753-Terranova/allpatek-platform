# Allpatek Platform

Backend de Allpatek. Expone una API REST para parcelas, contratos, bóveda de pagos, evidencias de hitos y alertas climáticas.

Stack: Java 27, Spring Boot 4, Spring Data JPA y MySQL 8. El servidor queda en el puerto **8090**. El frontend Angular en `http://localhost:4200` ya está permitido por CORS.

## Levantar en local

Necesitas MySQL 8 corriendo. En XAMPP basta con el usuario `root` sin clave. La base `allpatek-os` se crea sola la primera vez.

```bash
./mvnw spring-boot:run
```

- API: http://localhost:8090/api/v1
- Swagger: http://localhost:8090/swagger-ui/index.html

El perfil por defecto es `dev` (`ddl-auto=update`, SQL en consola). En `prod` el puerto pasa a `8080`.

## Rutas

Todo va bajo `/api/v1`:

| Recurso | Métodos | Notas |
|---|---|---|
| `/parcels` | GET, POST, PUT, DELETE | Parcela agrícola |
| `/agreements` | GET, POST, PUT, DELETE | Contrato. Si firman agricultor y comerciante, la parcela pasa a `in-production` |
| `/wallets` | GET, POST, PUT, DELETE | Tarjeta de la bóveda |
| `/evidence` | GET, POST, PUT, DELETE | Evidencia de cada hito |
| `/alerts` | GET | Clima con Open-Meteo, filtrable por `ownerId` |
| `/profiles` | GET, PUT | Perfil de usuario |

Los mensajes de validación salen en inglés o español según `Accept-Language`.

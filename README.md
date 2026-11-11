# Allpatek Platform

API REST en Java 27, Spring Boot 4 y MySQL. Puerto **8090**.

MySQL 8 en local, base `allpatek-os`, usuario `root`:

```bash
./mvnw spring-boot:run
```

- API: http://localhost:8090/api/v1
- Swagger: http://localhost:8090/swagger-ui/index.html

Rutas bajo `/api/v1`: `parcels`, `agreements`, `wallets`, `evidence`, `alerts` y `profiles`. Las cuatro primeras tienen GET, POST, PUT y DELETE. `alerts` consulta el clima con Open-Meteo. Si agricultor y comerciante firman el contrato, la parcela pasa a `in-production`.

Variables opcionales: `DATABASE_USER`, `DATABASE_PASSWORD`, `DATABASE_URL`, `DATABASE_PORT`, `DATABASE_NAME`, `PORT`. El perfil por defecto es `dev`.

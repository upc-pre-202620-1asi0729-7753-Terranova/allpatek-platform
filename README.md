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

Los mensajes de validación salen en inglés o español según `Accept-Language`.

---

## Cómo probar todos los endpoints en Swagger

Abre [http://localhost:8090/swagger-ui/index.html](http://localhost:8090/swagger-ui/index.html). Arriba a la derecha está **Authorize**. Ahí pegas el JWT. Casi todo, menos registro y login, pide token.

No hay cuentas precargadas. Las creas tú en Swagger, igual que en learning-center: `POST /api/v1/users` y luego `POST /api/v1/tokens`. El arranque solo deja los roles (`ROLE_FARMER`, `ROLE_MERCHANT`, `ROLE_ADMIN`).

Cambia el token cada vez que pases de agricultor a comerciante: **Authorize → Logout → Authorize** otra vez.

### 0. Crear cuenta y autorizar

1. `POST /api/v1/users` (agricultor o comerciante, JSON más abajo).
2. `POST /api/v1/tokens` con el mismo `username` y `password`.
3. Copia el campo `token` de la respuesta.
4. **Authorize** → `Bearer <pega-el-token>` → Authorize → Close.
5. El candado verde queda activo.
6. `POST /api/v1/profiles` con los datos de esa persona.

Guarda de la respuesta:

- `id` → userId
- `profileId` → lo usan parcelas, acuerdos y wallets
- `token` → Authorization

---

### 1. IAM (los dos roles)

Estos endpoints no son de agricultor ni de comerciante: son de la cuenta.

| Método | Ruta | Token | Qué hace |
|---|---|---|---|
| POST | `/api/v1/users` | no | Registro |
| POST | `/api/v1/tokens` | no | Login, devuelve JWT |
| GET | `/api/v1/users` | sí | Lista usuarios |
| GET | `/api/v1/users/{userId}` | sí | Un usuario |
| GET | `/api/v1/roles` | sí | `ROLE_FARMER`, `ROLE_MERCHANT`, `ROLE_ADMIN` |

Registro agricultor (`POST /users`):

```json
{
  "username": "agricultor.prueba@allpatek.local",
  "password": "Allpatek2026!",
  "roles": ["ROLE_FARMER"]
}
```

Registro comerciante:

```json
{
  "username": "comerciante.prueba@allpatek.local",
  "password": "Allpatek2026!",
  "roles": ["ROLE_MERCHANT"]
}
```

Después del registro: `POST /tokens` con ese usuario, autoriza, y crea el perfil (paso 2).

---

### 2. Profiles (los dos roles)

| Método | Ruta | Qué hace |
|---|---|---|
| POST | `/api/v1/profiles` | Crea el perfil de la cuenta logueada |
| GET | `/api/v1/profiles/me` | Perfil del token |
| GET | `/api/v1/profiles` | Lista |
| GET | `/api/v1/profiles/{id}` | Uno |
| PUT | `/api/v1/profiles/{id}` | Actualiza (usa el id de `/me`) |

Nombre solo letras. Teléfono 9 dígitos. DNI 8 dígitos. Si pones empresa, el RUC son 11 dígitos.

Agricultor (`POST /profiles`):

```json
{
  "fullName": "Gustavo Suyo",
  "phone": "987654321",
  "document": "40581234",
  "address": "Anexo Huayllapampa, Ayacucho",
  "email": "agricultor.prueba@allpatek.local",
  "company": "",
  "ruc": "",
  "activity": ""
}
```

Comerciante (`POST /profiles`). Si el perfil es nuevo y lleva empresa/RUC, escrow crea una wallet de demostración sola:

```json
{
  "fullName": "Cesar Egocheaga",
  "phone": "912345678",
  "document": "10876543",
  "address": "Av. La Marina 1200, Lima",
  "email": "comerciante.prueba@allpatek.local",
  "company": "Granos Andinos SAC",
  "ruc": "20601234567",
  "activity": "Comercio de granos"
}
```

`PUT /profiles/{id}` usa el mismo cuerpo, con el `email` de la cuenta.

Después de crear el perfil, `GET /profiles/me` debe devolver 200.

---

### 3. Recorrido agricultor

Autoriza con **Gustavo**.

#### 3.1 Parcels

| Método | Ruta | Quién |
|---|---|---|
| GET | `/api/v1/parcels` | ambos (lectura) |
| POST | `/api/v1/parcels` | solo agricultor |
| PUT | `/api/v1/parcels/{id}` | autenticado |
| DELETE | `/api/v1/parcels/{id}` | autenticado |

`POST /parcels`. `location` en `latitud,longitud` para que las alertas funcionen:

```json
{
  "id": "parcela-demo-1",
  "ownerId": 1,
  "name": "Parcela Palta",
  "status": "available",
  "area": "1000 m2",
  "location": "-12.0464,-77.0428",
  "soilType": "franco",
  "campaignCost": "4500",
  "durationMonths": 6,
  "startMonth": "septiembre",
  "crops": ["palta"],
  "imageUrl": "",
  "images": []
}
```

`ownerId` se rellena con el perfil del token si no lo pones. Prueba también `GET /parcels`, `PUT /parcels/parcela-demo-1` (mismo JSON, cambia `name`) y al final del recorrido `DELETE /parcels/parcela-demo-1`.

#### 3.2 Alertas

Contrato, unidades y errores: [Climate alerts API](docs/alerts-api.md).
Arquitectura, configuración y pruebas: [Alerts development](docs/alerts-development.md).


| Método | Ruta | Quién |
|---|---|---|
| GET | `/api/v1/alerts` | autenticado |

Filtra por el `profileId` del token y llama Open-Meteo. Si no hay parcelas con coordenadas, responde lista vacía.

#### 3.3 Firma del agricultor (después de que el comerciante cree el acuerdo)

| Método | Ruta | Quién |
|---|---|---|
| POST | `/api/v1/agreements/{id}/farmer-signature` | solo agricultor |

Sin body. Si ambos firmaron, la parcela pasa a `in_production`.

#### 3.4 Evidencia (el agricultor sube el hito)

| Método | Ruta | Quién |
|---|---|---|
| GET | `/api/v1/evidence` | autenticado |
| POST | `/api/v1/evidence` | solo agricultor |
| PUT | `/api/v1/evidence/{id}` | autenticado |
| DELETE | `/api/v1/evidence/{id}` | autenticado |

`POST /evidence` (milestone de 1 a 4):

```json
{
  "id": "evidence-hito-1",
  "parcelId": "parcela-demo-1",
  "agreementId": "acuerdo-demo-1",
  "milestone": 1,
  "status": "review",
  "notes": "Siembra completada",
  "fileName": "hito-1.jpg",
  "image": "data:image/jpeg;base64,AAA"
}
```

`PUT /evidence/evidence-hito-1` usa el mismo JSON y cambia `notes`. `DELETE` al terminar, si quieres borrar.

---

### 4. Recorrido comerciante

Autoriza con **Cesar** (cambia el token).

#### 4.1 Acuerdos

| Método | Ruta | Quién |
|---|---|---|
| GET | `/api/v1/agreements` | autenticado |
| GET | `/api/v1/agreements/{id}` | autenticado |
| POST | `/api/v1/agreements` | solo comerciante |
| PUT | `/api/v1/agreements/{id}` | autenticado |
| DELETE | `/api/v1/agreements/{id}` | autenticado |

`POST /agreements` (el comerciante pide el contrato; `merchantSigned` queda en true):

```json
{
  "id": "acuerdo-demo-1",
  "parcelId": "parcela-demo-1",
  "merchantName": "Cesar Egocheaga",
  "merchantProfileId": 2,
  "merchantSigned": true,
  "farmerSigned": false,
  "createdAt": "2026-11-24"
}
```

`GET /agreements` y `GET /agreements/acuerdo-demo-1`. Vuelve al token de Gustavo y haz `POST /agreements/acuerdo-demo-1/farmer-signature`.

`PUT /agreements/acuerdo-demo-1` actualiza el recurso completo. `DELETE` al final si quieres limpiar.

#### 4.2 Wallets (bóveda)

| Método | Ruta | Quién |
|---|---|---|
| GET | `/api/v1/wallets` | autenticado |
| POST | `/api/v1/wallets` | solo comerciante |
| PUT | `/api/v1/wallets/{id}` | solo comerciante |
| DELETE | `/api/v1/wallets/{id}` | autenticado |

`GET /wallets` primero: el perfil comerciante demo puede tener ya `wallet-2`. Si no hay ninguna:

```json
{
  "id": "wallet-cesar",
  "profileId": 2,
  "holder": "Cesar Egocheaga",
  "number": "4242424242424242",
  "cvv": "123",
  "last4": "4242",
  "expiry": "12/28",
  "brand": "Visa",
  "openingCents": 50000
}
```

No se guarda PAN ni CVV. La respuesta trae `last4` y el saldo en céntimos (`50000` = S/ 500.00).

`PUT /wallets/wallet-cesar` con el mismo cuerpo. `DELETE /wallets/wallet-cesar` al final.

#### 4.3 Verificar y liberar evidencia

Con el token de Cesar, después de que Gustavo subió el hito:

| Método | Ruta | Quién |
|---|---|---|
| POST | `/api/v1/evidence/{id}/verify` | solo comerciante |
| POST | `/api/v1/evidence/{id}/release` | solo comerciante |

Sin body.

1. `POST /evidence/evidence-hito-1/verify` → status `verified`
2. `POST /evidence/evidence-hito-1/release` → status `accepted` y escrow acredita S/ 250 al wallet del comerciante del acuerdo

Si sueltas sin verificar, responde 422.

---

### 5. Orden recomendado (un ciclo completo)

1. `POST /tokens` Gustavo → Authorize.
2. `GET /profiles/me`, `GET /users`, `GET /roles`, `GET /users/{userId}`.
3. `POST /parcels` (`parcela-demo-1`).
4. `GET /parcels`, `GET /alerts`.
5. Authorize con Cesar.
6. `GET /profiles/me`, `GET /wallets`.
7. `POST /agreements` (`acuerdo-demo-1` sobre `parcela-demo-1`).
8. Authorize con Gustavo.
9. `POST /agreements/acuerdo-demo-1/farmer-signature` → parcela `in_production`.
10. `POST /evidence` (`evidence-hito-1`).
11. Authorize con Cesar.
12. `POST /evidence/evidence-hito-1/verify`.
13. `POST /evidence/evidence-hito-1/release`.
14. `GET /wallets` y confirma que `openingCents` subió 25000.
15. Prueba `PUT` de parcela, acuerdo, evidencia, perfil y wallet.
16. Prueba `DELETE` de evidencia, acuerdo, wallet y parcela (en ese orden).

---

### 6. Qué no debe pasar (comprueba roles)

Con token de **agricultor**:

- `POST /agreements` → 403
- `POST /wallets` → 403
- `POST /evidence/{id}/verify` → 403

Con token de **comerciante**:

- `POST /parcels` → 403
- `POST /evidence` → 403
- `POST /agreements/{id}/farmer-signature` → 403

Sin token: cualquier GET de negocio → 401. `POST /users` y `POST /tokens` sí van sin Authorize.

---

### 7. Lista de todos los endpoints

| Tag Swagger | Método | Ruta | Token | Rol extra |
|---|---|---|---|---|
| Users | POST | `/api/v1/users` | no | — |
| Users | GET | `/api/v1/users` | sí | — |
| Users | GET | `/api/v1/users/{userId}` | sí | — |
| Tokens | POST | `/api/v1/tokens` | no | — |
| Roles | GET | `/api/v1/roles` | sí | — |
| Profiles | POST | `/api/v1/profiles` | sí | — |
| Profiles | GET | `/api/v1/profiles/me` | sí | — |
| Profiles | GET | `/api/v1/profiles` | sí | — |
| Profiles | GET | `/api/v1/profiles/{id}` | sí | — |
| Profiles | PUT | `/api/v1/profiles/{id}` | sí | — |
| parcels | GET | `/api/v1/parcels` | sí | — |
| parcels | POST | `/api/v1/parcels` | sí | agricultor |
| parcels | PUT | `/api/v1/parcels/{id}` | sí | — |
| parcels | DELETE | `/api/v1/parcels/{id}` | sí | — |
| agreements | GET | `/api/v1/agreements` | sí | — |
| agreements | GET | `/api/v1/agreements/{id}` | sí | — |
| agreements | POST | `/api/v1/agreements` | sí | comerciante |
| agreements | POST | `/api/v1/agreements/{id}/farmer-signature` | sí | agricultor |
| agreements | PUT | `/api/v1/agreements/{id}` | sí | — |
| agreements | DELETE | `/api/v1/agreements/{id}` | sí | — |
| tracking | GET | `/api/v1/evidence` | sí | — |
| tracking | POST | `/api/v1/evidence` | sí | agricultor |
| tracking | POST | `/api/v1/evidence/{id}/verify` | sí | comerciante |
| tracking | POST | `/api/v1/evidence/{id}/release` | sí | comerciante |
| tracking | PUT | `/api/v1/evidence/{id}` | sí | — |
| tracking | DELETE | `/api/v1/evidence/{id}` | sí | — |
| escrow | GET | `/api/v1/wallets` | sí | — |
| escrow | POST | `/api/v1/wallets` | sí | comerciante |
| escrow | PUT | `/api/v1/wallets/{id}` | sí | comerciante |
| escrow | DELETE | `/api/v1/wallets/{id}` | sí | — |
| alerts | GET | `/api/v1/alerts` | sí | — |

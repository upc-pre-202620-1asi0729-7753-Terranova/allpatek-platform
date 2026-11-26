# Developing and verifying climate alerts

## Context boundaries

`AlertsController` reads the authenticated account's username and resolves its profile
through `ExternalProfileService`. That adapter calls `ProfilesContextFacade`, which
exposes only the profile identifier. Alerts does not import the profiles domain or
infrastructure packages. A missing profile stops the request before querying parcels.

`ClimateQueryServiceImpl` handles `GetClimateAlertsQuery`. It uses `ExternalParcelService`
to translate published parcel location DTOs into alert coordinates. The parcels facade
rejects missing owners and its repository filters the portfolio by owner in the database.

Domain objects validate coordinates and measurements. `WeatherRiskPolicy` contains the
existing thresholds. `OpenMeteoResponseParser` validates forecast shape, numeric fields,
daily arrays, response counts and supported weather codes. Expected failures use
`Result` and `ApplicationError`; the REST boundary uses the shared response assembler.

## Weather provider

The existing `RestClient` adapter sends one forecast request for the owned parcel
coordinates. It keeps the original order and produces a separate reading for each
parcel, including parcels that share coordinates. The endpoint is configured by
`weather.open-meteo.base-url`, whose default is `https://api.open-meteo.com/v1/forecast`.

The adapter requests Celsius, millimetres and kilometres per hour, using the first
daily forecast in the provider's local timezone. Invalid or missing measurements
fail the request instead of becoming synthetic zero-valued readings.

## Verification

Install JDK 27 and set `JAVA_HOME` before using the Maven wrapper:

```powershell
.\mvnw.cmd -B clean verify
```

On Unix use `./mvnw -B clean verify`. Domain tests use real value objects and readings.
Application tests use JUnit and Mockito to replace injected facade and weather service
dependencies. Parser tests exercise provider payloads without making HTTP requests.
REST tests verify resource shapes, owner selection and ProblemDetail responses.

Full-context tests use Spring Boot Test and an isolated H2 database in MySQL compatibility
mode. They verify authentication, missing profiles and the database owner predicate.
Test data is rolled back. H2 and security test helpers are restricted to test scope.
The test profile points weather requests at a closed loopback port.

Tests require no production credentials and do not contact Open-Meteo. H2 verifies the
JPA query but does not replace deployment checks on MySQL or provider availability checks.

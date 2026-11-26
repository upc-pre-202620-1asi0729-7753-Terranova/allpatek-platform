# Developing and verifying climate alerts

## Context boundaries

The REST controller resolves the current profile and issues an owner-scoped query.
`ExternalParcelService` translates the parcels facade's location DTOs into alert
coordinates. The parcels facade rejects missing owners and executes an owner predicate
through its repository; it does not load every owner's portfolio into memory.

Domain objects validate coordinates and physical measurement ranges. `WeatherRiskPolicy`
contains the existing thresholds. `OpenMeteoResponseParser` validates forecast shape,
numeric fields, daily arrays, response counts and supported weather codes.

The provider adapter sends unique coordinate pairs in bounded batches. After fetching,
it restores the original parcel identities and order. Deduplication lasts for a single
request: there is no cross-account or persistent weather cache and no stale fallback.
Requests are sequential; no automatic retry loop amplifies rate-limited requests.

## Configuration

| Spring property | Default | Constraint |
| --- | --- | --- |
| weather.open-meteo.base-url | https://api.open-meteo.com/v1/forecast | configured provider endpoint |
| weather.open-meteo.connect-timeout-ms | 2000 | positive milliseconds |
| weather.open-meteo.read-timeout-ms | 5000 | positive milliseconds |
| weather.open-meteo.batch-size | 50 | 1 through 100 coordinate pairs |

Timeouts apply to each provider request. Several sequential batches can take longer
than a single timeout. Deployment request limits should account for this behavior.

## Verification

Install JDK 27 and set `JAVA_HOME` before using the Maven wrapper:

```powershell
.\mvnw.cmd -B clean verify
```

On Unix use `./mvnw -B clean verify`. Tests require no production credentials.
The test profile uses an in-memory H2 database in MySQL compatibility mode with
create/drop schema management. Only test scope includes H2 and security test helpers.
H2 verifies the JPA owner predicate; it does not replace deployment checks on MySQL.

Provider tests use Spring's mock HTTP server. The test profile points weather requests
at a closed loopback port so accidental provider calls fail locally. Regression tests
cover missing profiles, owner filtering, malformed coordinates, zero-valued readings,
weather rules, invalid payloads, transport failures, batching and REST resources.
Full-context tests exercise the security filter chain and database-backed facade.

The tests do not contact Open-Meteo or validate availability of a deployed instance.

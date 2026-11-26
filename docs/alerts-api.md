# Climate alerts API

`GET /api/v1/alerts` requires a bearer token and an existing account profile.
The owner is resolved from the authenticated account; clients cannot select an owner.
Responses use `Cache-Control: no-store` on success and application errors.

## Success

A JSON array contains one climate reading per owned parcel. An account without parcels
receives `200` with `[]` and no request is sent to the weather provider.
Parcel identifiers, names and coordinates remain attached to their own readings even
when multiple parcels share a coordinate pair. Translation keys in `alerts` remain
compatible with the web application's existing resource contract.

| Measurement | Unit or meaning |
| --- | --- |
| temperature, apparent, tempMin, tempMax | degrees Celsius |
| humidity, cloudCover | percentage, 0 through 100 |
| precipitation, precipitationSum | millimetres |
| windSpeed, windMax | kilometres per hour |
| weatherCode | supported Open-Meteo WMO code |

Current measurements and the first daily forecast use the provider's local timezone.
The request explicitly selects a single forecast day. Zero is a valid measurement;
missing data is never represented as a synthetic zero. No observation timestamp is
currently included in the public response.

## Existing risk rules

- Frost: daily minimum at or below 3 degrees Celsius, critical.
- Rain: daily total at least 10 mm or current precipitation at least 2 mm.
  Severity becomes critical at a daily total of 20 mm.
- Wind: current speed at least 30 km/h or daily maximum at least 40 km/h, moderate.
- Normal: returned only when no risk rule matches.

These product thresholds are preserved by the corrections. Multiple risks may coexist.

## Errors

Errors use the application's ProblemDetail format and include a `code`.

| HTTP status | Code or cause |
| --- | --- |
| 401 | missing or invalid authentication |
| 404 | PROFILE_NOT_FOUND |
| 400 | VALIDATION_ERROR for invalid coordinates in an owned parcel |
| 502 | WEATHER_BAD_GATEWAY: incomplete, malformed or rejected forecast |
| 503 | WEATHER_UNAVAILABLE: connection failure, provider rate limit or server failure |
| 504 | WEATHER_TIMEOUT: provider connection/read timeout |

If one owned parcel has invalid coordinates, or one forecast batch fails, the request
fails as a whole. Partial arrays are not reported as complete success. Provider response
bodies and exception messages are not forwarded in weather error details.

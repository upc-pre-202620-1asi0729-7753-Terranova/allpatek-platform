package com.allpatekplatform.alerts.infrastructure.weather;
import com.fasterxml.jackson.databind.*;
final class ForecastFixtures {
    private ForecastFixtures() {}
    static JsonNode payload() throws Exception { return new ObjectMapper().readTree(json()); }
    static String json() { return """
    {"current":{"temperature_2m":18,"apparent_temperature":0,"relative_humidity_2m":50,
    "precipitation":0,"wind_speed_10m":0,"cloud_cover":0,"weather_code":0},
    "daily":{"temperature_2m_min":[0],"temperature_2m_max":[20],"precipitation_sum":[0],"wind_speed_10m_max":[0]}}
    """; }
}

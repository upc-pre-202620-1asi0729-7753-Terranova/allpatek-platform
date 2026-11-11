package com.allpatekplatform.alerts.infrastructure.weather;

import com.allpatekplatform.alerts.application.internal.outboundservices.WeatherForecastService;
import com.allpatekplatform.alerts.domain.model.aggregates.ClimateReading;
import com.allpatekplatform.alerts.domain.model.valueobjects.ParcelCoordinates;
import com.allpatekplatform.alerts.domain.model.valueobjects.WeatherSnapshot;
import com.allpatekplatform.shared.application.result.ApplicationError;
import com.allpatekplatform.shared.application.result.Result;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OpenMeteoWeatherForecastService implements WeatherForecastService {

    private static final String CURRENT =
            "temperature_2m,apparent_temperature,relative_humidity_2m,precipitation,wind_speed_10m,cloud_cover,weather_code";
    private static final String DAILY =
            "temperature_2m_max,temperature_2m_min,precipitation_sum,wind_speed_10m_max";

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private final RestClient restClient;
    private final String baseUrl;

    public OpenMeteoWeatherForecastService(@Value("${weather.open-meteo.base-url}") String baseUrl) {
        this.restClient = RestClient.builder().build();
        this.baseUrl = baseUrl;
    }

    @Override
    public Result<List<ClimateReading>, ApplicationError> forecast(List<ParcelCoordinates> places) {
        if (places == null || places.isEmpty()) {
            return Result.success(List.of());
        }
        try {
            URI uri = UriComponentsBuilder.fromUriString(baseUrl)
                    .queryParam("latitude", join(places, ParcelCoordinates::latitude))
                    .queryParam("longitude", join(places, ParcelCoordinates::longitude))
                    .queryParam("current", CURRENT)
                    .queryParam("daily", DAILY)
                    .queryParam("forecast_days", "2")
                    .queryParam("timezone", "auto")
                    .build(true)
                    .toUri();
            String json = restClient.get()
                    .uri(uri)
                    .retrieve()
                    .body(String.class);
            JsonNode payload = json == null || json.isBlank() ? null : MAPPER.readTree(json);
            List<JsonNode> blocks = blocks(payload, places.size());
            List<ClimateReading> readings = new ArrayList<>();
            for (int index = 0; index < places.size(); index++) {
                readings.add(toReading(places.get(index), blocks.get(index)));
            }
            return Result.success(List.copyOf(readings));
        } catch (Exception exception) {
            return Result.failure(ApplicationError.unexpected("OPEN_METEO", exception.getMessage()));
        }
    }

    private static String join(List<ParcelCoordinates> places, java.util.function.ToDoubleFunction<ParcelCoordinates> value) {
        return places.stream()
                .map(place -> Double.toString(value.applyAsDouble(place)))
                .collect(Collectors.joining(","));
    }

    private static List<JsonNode> blocks(JsonNode payload, int expected) {
        List<JsonNode> blocks = new ArrayList<>();
        if (payload != null && payload.isArray()) {
            payload.forEach(blocks::add);
        } else if (payload != null) {
            blocks.add(payload);
        }
        while (blocks.size() < expected) {
            blocks.add(null);
        }
        return blocks;
    }

    private static ClimateReading toReading(ParcelCoordinates place, JsonNode block) {
        JsonNode current = block == null ? null : block.path("current");
        JsonNode daily = block == null ? null : block.path("daily");
        double temperature = number(current, "temperature_2m");
        return ClimateReading.of(place, new WeatherSnapshot(
                temperature,
                firstNonZero(number(current, "apparent_temperature"), temperature),
                number(current, "relative_humidity_2m"),
                number(current, "precipitation"),
                number(current, "wind_speed_10m"),
                number(current, "cloud_cover"),
                (int) number(current, "weather_code"),
                firstNonZero(first(daily, "temperature_2m_min"), temperature),
                firstNonZero(first(daily, "temperature_2m_max"), temperature),
                first(daily, "precipitation_sum"),
                firstNonZero(first(daily, "wind_speed_10m_max"), number(current, "wind_speed_10m"))));
    }

    private static double number(JsonNode node, String field) {
        if (node == null || node.isMissingNode() || node.path(field).isMissingNode()) {
            return 0;
        }
        return node.path(field).asDouble(0);
    }

    private static double first(JsonNode node, String field) {
        if (node == null || node.isMissingNode()) {
            return 0;
        }
        JsonNode values = node.path(field);
        if (values.isArray() && !values.isEmpty()) {
            return values.get(0).asDouble(0);
        }
        return 0;
    }

    private static double firstNonZero(double value, double fallback) {
        return value == 0 ? fallback : value;
    }
}

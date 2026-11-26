package com.allpatekplatform.alerts.infrastructure.weather;
import com.allpatekplatform.alerts.domain.model.aggregates.ClimateReading;
import com.allpatekplatform.alerts.domain.model.valueobjects.*;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.ArrayList;
import java.util.List;
final class OpenMeteoResponseParser {
    private OpenMeteoResponseParser() {}
    static List<JsonNode> blocks(JsonNode payload, int expected) {
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

    static ClimateReading toReading(ParcelCoordinates place, JsonNode block) {
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

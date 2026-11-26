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
        if (blocks.size() != expected)
            throw new IllegalArgumentException("Forecast response count does not match requested locations.");
        for (JsonNode block : blocks) {
            if (!block.isObject() || block.path("error").asBoolean(false)
                    || !block.path("current").isObject() || !block.path("daily").isObject())
                throw new IllegalArgumentException("Invalid forecast response structure.");
        }
        return blocks;
    }

    static ClimateReading toReading(ParcelCoordinates place, JsonNode block) {
        JsonNode current = block == null ? null : block.path("current");
        JsonNode daily = block == null ? null : block.path("daily");
        double temperature = number(current, "temperature_2m");
        return ClimateReading.of(place, new WeatherSnapshot(
                temperature,
                number(current, "apparent_temperature"),
                number(current, "relative_humidity_2m"),
                number(current, "precipitation"),
                number(current, "wind_speed_10m"),
                number(current, "cloud_cover"),
                weatherCode(current),
                first(daily, "temperature_2m_min"),
                first(daily, "temperature_2m_max"),
                first(daily, "precipitation_sum"),
                first(daily, "wind_speed_10m_max")));
    }

    private static double number(JsonNode node, String field) {
        return measurement(node == null ? null : node.get(field));
    }

    private static double measurement(JsonNode value) {
        if (value == null || !value.isNumber() || !Double.isFinite(value.doubleValue()))
            throw new IllegalArgumentException("Missing or invalid weather measurement.");
        return value.doubleValue();
    }

    private static int weatherCode(JsonNode node) {
        double code = number(node, "weather_code");
        if (code != Math.rint(code) || code < 0 || code > 99)
            throw new IllegalArgumentException("Invalid weather code.");
        return (int) code;
    }

    private static double first(JsonNode node, String field) {
        JsonNode values = node == null ? null : node.get(field);
        if (values == null || !values.isArray() || values.isEmpty())
            throw new IllegalArgumentException("Missing daily weather series.");
        return measurement(values.get(0));
    }
}

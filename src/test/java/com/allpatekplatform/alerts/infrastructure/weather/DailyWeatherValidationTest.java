package com.allpatekplatform.alerts.infrastructure.weather;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import static com.allpatekplatform.alerts.support.ClimateFixtures.*;

class DailyWeatherValidationTest {

@Test void rejectsAbsentAndMalformedDailySeries() throws Exception {
    for (String field : new String[]{"temperature_2m_min","temperature_2m_max","precipitation_sum","wind_speed_10m_max"}) {
        for (String value : new String[]{"null", "[]", "[null]", "[\"0\"]", "0"}) {
            var root = ForecastFixtures.payload();
            ((ObjectNode) root.get("daily")).set(field, new ObjectMapper().readTree(value));
            assertThrows(IllegalArgumentException.class, () -> OpenMeteoResponseParser.toReading(place(), root));
        }
    }
}
@Test void usesTheFirstRequestedForecastDay() throws Exception {
    var root = ForecastFixtures.payload();
    ((ObjectNode) root.get("daily")).set("temperature_2m_min", new ObjectMapper().readTree("[-1,10]"));
    assertEquals(-1, OpenMeteoResponseParser.toReading(place(), root).weather().tempMin());
}

}

package com.allpatekplatform.alerts.infrastructure.weather;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import static com.allpatekplatform.alerts.support.ClimateFixtures.*;

class CurrentWeatherValidationTest {

@Test void absentNullAndTextMeasurementsCannotBecomeZero() throws Exception {
    for (String field : new String[]{"temperature_2m","apparent_temperature","relative_humidity_2m","precipitation","wind_speed_10m","cloud_cover","weather_code"}) {
        var root = ForecastFixtures.payload();
        ((ObjectNode) root.get("current")).remove(field);
        assertThrows(IllegalArgumentException.class, () -> OpenMeteoResponseParser.toReading(place(), root));
    }
    for (String value : new String[]{"null", "\"18\"", "true", "{}"}) {
        var root = ForecastFixtures.payload();
        ((ObjectNode) root.get("current")).set("temperature_2m", new ObjectMapper().readTree(value));
        assertThrows(IllegalArgumentException.class, () -> OpenMeteoResponseParser.toReading(place(), root));
    }
}
@Test void fractionalCodesAreNotTruncated() throws Exception {
    var root = ForecastFixtures.payload();
    ((ObjectNode) root.get("current")).put("weather_code", 2.5);
    assertThrows(IllegalArgumentException.class, () -> OpenMeteoResponseParser.toReading(place(), root));
}

}

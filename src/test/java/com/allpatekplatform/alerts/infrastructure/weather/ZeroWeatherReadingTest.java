package com.allpatekplatform.alerts.infrastructure.weather;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import static com.allpatekplatform.alerts.support.ClimateFixtures.*;

class ZeroWeatherReadingTest {

@Test void zeroIsAReadingAndStillTriggersFrost() throws Exception {
    var reading = OpenMeteoResponseParser.toReading(place(), ForecastFixtures.payload());
    assertEquals(18, reading.weather().temperature());
    assertEquals(0, reading.weather().apparent());
    assertEquals(0, reading.weather().tempMin());
    assertEquals(0, reading.weather().windMax());
    assertEquals("alerts.frostTitle", reading.alerts().getFirst().message().titleKey());
}

}

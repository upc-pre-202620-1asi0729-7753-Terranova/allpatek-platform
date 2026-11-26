package com.allpatekplatform.alerts.infrastructure.weather;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import static com.allpatekplatform.alerts.support.ClimateFixtures.*;

class ForecastCardinalityTest {

@Test void neverPadsMissingLocationsWithInventedWeather() throws Exception {
    var root = ForecastFixtures.payload();
    assertThrows(IllegalArgumentException.class, () -> OpenMeteoResponseParser.blocks(root, 2));
    assertThrows(IllegalArgumentException.class, () -> OpenMeteoResponseParser.blocks(null, 1));
    assertThrows(IllegalArgumentException.class, () -> OpenMeteoResponseParser.blocks(new ObjectMapper().readTree("[]"), 1));
    assertEquals(1, OpenMeteoResponseParser.blocks(root, 1).size());
    var two = new ObjectMapper().createArrayNode().add(root).add(root);
    assertEquals(2, OpenMeteoResponseParser.blocks(two, 2).size());
    assertThrows(IllegalArgumentException.class, () -> OpenMeteoResponseParser.blocks(two, 1));
}

}

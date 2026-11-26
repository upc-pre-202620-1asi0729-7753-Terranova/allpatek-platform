package com.allpatekplatform.alerts.domain.services;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.allpatekplatform.alerts.domain.model.aggregates.ClimateReading;
import com.allpatekplatform.alerts.domain.model.valueobjects.AlertTone;
import static com.allpatekplatform.alerts.support.ClimateFixtures.*;

class RainRiskTest {

@Test void triggersAtEitherRainThreshold() {
    assertFalse(WeatherRiskPolicy.rain(weather(10,1.99,9.99,0,0)));
    assertTrue(WeatherRiskPolicy.rain(weather(10,2,0,0,0)));
    assertTrue(WeatherRiskPolicy.rain(weather(10,0,10,0,0)));
}
@Test void escalatesSeverityAtTwentyMillimetres() {
    assertEquals(AlertTone.moderate, ClimateReading.of(place(),weather(10,0,19.99,0,0)).alerts().getFirst().tone());
    assertEquals(AlertTone.critical, ClimateReading.of(place(),weather(10,0,20,0,0)).alerts().getFirst().tone());
}

}

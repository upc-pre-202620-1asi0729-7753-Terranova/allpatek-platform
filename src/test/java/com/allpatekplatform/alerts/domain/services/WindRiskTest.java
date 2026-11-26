package com.allpatekplatform.alerts.domain.services;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.allpatekplatform.alerts.domain.model.aggregates.ClimateReading;
import com.allpatekplatform.alerts.domain.model.valueobjects.AlertTone;
import static com.allpatekplatform.alerts.support.ClimateFixtures.*;

class WindRiskTest {

@Test void triggersAtEitherWindThreshold() {
    assertFalse(WeatherRiskPolicy.wind(weather(10,0,0,29.99,39.99)));
    assertTrue(WeatherRiskPolicy.wind(weather(10,0,0,30,0)));
    assertTrue(WeatherRiskPolicy.wind(weather(10,0,0,0,40)));
}
@Test void preservesConcurrentRisks() {
    var alerts = ClimateReading.of(place(), weather(0,2,20,30,40)).alerts();
    assertEquals(3, alerts.size());
    assertEquals(java.util.List.of("alerts.frostTitle","alerts.floodTitle","alerts.windTitle"),
            alerts.stream().map(a -> a.message().titleKey()).toList());
}

}

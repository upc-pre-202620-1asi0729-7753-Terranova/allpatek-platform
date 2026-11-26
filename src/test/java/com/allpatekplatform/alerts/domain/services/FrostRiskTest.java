package com.allpatekplatform.alerts.domain.services;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.allpatekplatform.alerts.domain.model.aggregates.ClimateReading;
import com.allpatekplatform.alerts.domain.model.valueobjects.AlertTone;
import static com.allpatekplatform.alerts.support.ClimateFixtures.*;

class FrostRiskTest {

@Test void includesFreezingAndThresholdTemperatures() {
    for (double min : new double[]{-5,0,3}) {
        var alerts = ClimateReading.of(place(), weather(min,0,0,0,0)).alerts();
        assertEquals("alerts.frostTitle", alerts.getFirst().message().titleKey());
        assertEquals(AlertTone.critical, alerts.getFirst().tone());
    }
    assertFalse(WeatherRiskPolicy.frost(weather(3.001,0,0,0,0)));
}

}

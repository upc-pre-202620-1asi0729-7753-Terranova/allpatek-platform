package com.allpatekplatform.alerts.domain.model.valueobjects;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static com.allpatekplatform.alerts.support.ClimateFixtures.*;

class WeatherSnapshotTest {

@Test void rejectsInvalidPhysicalMeasurements() {
    assertThrows(IllegalArgumentException.class, () -> weather(Double.NaN,0,0,0,0));
    assertThrows(IllegalArgumentException.class, () -> weather(3,-1,0,0,0));
    assertThrows(IllegalArgumentException.class, () -> weather(3,0,-1,0,0));
    assertThrows(IllegalArgumentException.class, () -> weather(3,0,0,-1,0));
    assertThrows(IllegalArgumentException.class, () -> weather(3,0,0,0,Double.POSITIVE_INFINITY));
    assertThrows(IllegalArgumentException.class, () -> weather(26,0,0,0,0));
    assertThrows(IllegalArgumentException.class, () -> new WeatherSnapshot(18,18,101,0,0,50,0,3,25,0,0));
    assertThrows(IllegalArgumentException.class, () -> new WeatherSnapshot(18,18,50,0,0,-1,0,3,25,0,0));
}
@Test void acceptsFreezingTemperaturesAndCalmDryConditions() {
    assertEquals(0, weather(0,0,0,0,0).tempMin());
    assertDoesNotThrow(() -> weather(-20,0,0,0,0));
}

}

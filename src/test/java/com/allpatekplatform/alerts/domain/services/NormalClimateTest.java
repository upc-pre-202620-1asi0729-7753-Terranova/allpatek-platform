package com.allpatekplatform.alerts.domain.services;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.allpatekplatform.alerts.domain.model.aggregates.ClimateReading;
import com.allpatekplatform.alerts.domain.model.valueobjects.AlertTone;
import static com.allpatekplatform.alerts.support.ClimateFixtures.*;

class NormalClimateTest {

@Test void normalAlertAppearsOnlyWithoutRisks() {
    var alerts = ClimateReading.of(place(), weather(10,0,0,0,0)).alerts();
    assertEquals(1, alerts.size());
    assertEquals("alerts.okTitle", alerts.getFirst().message().titleKey());
    assertEquals(AlertTone.normal, alerts.getFirst().tone());
    assertThrows(UnsupportedOperationException.class, alerts::clear);
    assertTrue(ClimateReading.of(place(),weather(0,2,20,30,40)).alerts().stream()
            .noneMatch(a -> a.tone() == AlertTone.normal));
}

}

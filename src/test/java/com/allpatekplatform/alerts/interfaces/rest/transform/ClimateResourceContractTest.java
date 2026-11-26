package com.allpatekplatform.alerts.interfaces.rest.transform;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.allpatekplatform.alerts.domain.model.aggregates.ClimateReading;
import static com.allpatekplatform.alerts.support.ClimateFixtures.*;

class ClimateResourceContractTest {

@Test void preservesMeasurementsParcelIdentityAndTranslationKeys() {
    var resource = ClimateReadingResourceAssembler.toResourceFromEntity(ClimateReading.of(place(),weather(0,2,20,30.4,40.6)));
    assertEquals("p1",resource.parcelId());
    assertEquals("Farm",resource.parcelName());
    assertEquals(-12,resource.latitude());
    assertEquals(0,resource.tempMin());
    assertEquals(30.4,resource.windSpeed());
    assertEquals("clear",resource.condition());
    assertEquals(3,resource.alerts().size());
    assertEquals("alerts.frostTitle",resource.alerts().getFirst().titleKey());
    assertEquals("critical",resource.alerts().getFirst().tone());
    assertEquals(30,resource.alerts().getFirst().windSpeed());
    assertEquals(41,resource.alerts().getFirst().windMax());
}

}

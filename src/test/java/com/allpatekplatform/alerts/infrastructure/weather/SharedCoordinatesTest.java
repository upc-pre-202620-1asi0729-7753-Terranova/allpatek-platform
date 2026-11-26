package com.allpatekplatform.alerts.infrastructure.weather;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.http.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;
import static com.allpatekplatform.alerts.support.ClimateFixtures.*;
import java.util.List;
import com.allpatekplatform.alerts.domain.model.valueobjects.ParcelCoordinates;

class SharedCoordinatesTest {

@Test void oneForecastStillProducesSeparateParcelAlerts() {
    var client = ForecastClientFixture.client(1);
    client.server().expect(method(HttpMethod.GET)).andRespond(withSuccess(ForecastFixtures.json(),MediaType.APPLICATION_JSON));
    var readings = client.service().forecast(List.of(place(),ParcelCoordinates.fromLocation("p2","Second","-12,-77"))).toOptional().orElseThrow();
    assertEquals(2,readings.size());
    assertEquals("p1",readings.getFirst().place().parcelId().value());
    assertEquals("p2",readings.get(1).place().parcelId().value());
    assertEquals("Second",readings.get(1).alerts().getFirst().place().name().value());
    assertEquals(readings.getFirst().weather(),readings.get(1).weather());
    client.server().verify();
}

}

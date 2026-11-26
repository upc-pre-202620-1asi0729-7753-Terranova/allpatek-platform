package com.allpatekplatform.alerts.infrastructure.weather;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.http.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;
import static com.allpatekplatform.alerts.support.ClimateFixtures.*;
import java.util.List;
import com.allpatekplatform.alerts.domain.model.valueobjects.ParcelCoordinates;

class WeatherBatchTest {

@Test void batchesPreserveAllParcelIdentitiesAndOrder() {
    var client = ForecastClientFixture.client(2);
    client.server().expect(method(HttpMethod.GET)).andRespond(withSuccess("["+ForecastFixtures.json()+","+ForecastFixtures.json()+"]",MediaType.APPLICATION_JSON));
    client.server().expect(method(HttpMethod.GET)).andRespond(withSuccess(ForecastFixtures.json(),MediaType.APPLICATION_JSON));
    var places = List.of(place(), ParcelCoordinates.fromLocation("p2","Second","1,1"), ParcelCoordinates.fromLocation("p3","Third","2,2"));
    var readings = client.service().forecast(places).toOptional().orElseThrow();
    assertEquals(List.of("p1","p2","p3"),readings.stream().map(r->r.place().parcelId().value()).toList());
    client.server().verify();
}
@Test void laterBatchFailureDoesNotReturnPartialSuccess() {
    var client = ForecastClientFixture.client(1);
    client.server().expect(method(HttpMethod.GET)).andRespond(withSuccess(ForecastFixtures.json(),MediaType.APPLICATION_JSON));
    client.server().expect(method(HttpMethod.GET)).andRespond(withStatus(HttpStatus.SERVICE_UNAVAILABLE));
    assertTrue(client.service().forecast(List.of(place(),ParcelCoordinates.fromLocation("p2","Second","1,1"))).isFailure());
    client.server().verify();
}
@Test void rejectsUnboundedAndZeroBatchSizes() {
    assertThrows(IllegalArgumentException.class, () -> ForecastClientFixture.client(0));
    assertThrows(IllegalArgumentException.class, () -> ForecastClientFixture.client(101));
}

}

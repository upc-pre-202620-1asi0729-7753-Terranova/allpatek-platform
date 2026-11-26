package com.allpatekplatform.alerts.infrastructure.weather;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.http.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;
import static com.allpatekplatform.alerts.support.ClimateFixtures.*;
import java.util.List;

class WeatherClientTest {

@Test void decodesSuccessfulHttpForecast() {
    var client = ForecastClientFixture.client();
    client.server().expect(method(HttpMethod.GET)).andRespond(withSuccess(ForecastFixtures.json(), MediaType.APPLICATION_JSON));
    var readings = client.service().forecast(List.of(place())).toOptional().orElseThrow();
    assertEquals(1,readings.size());
    assertEquals(0,readings.getFirst().weather().tempMin());
    client.server().verify();
}
@Test void noParcelsMeansNoHttpRequests() {
    var client = ForecastClientFixture.client();
    assertTrue(client.service().forecast(List.of()).toOptional().orElseThrow().isEmpty());
    client.server().verify();
}
@Test void invalidTimeoutConfigurationFailsAtStartup() {
    var config = new OpenMeteoClientConfiguration();
    assertThrows(IllegalArgumentException.class, () -> config.client(0,5000));
    assertThrows(IllegalArgumentException.class, () -> config.client(2000,-1));
    assertNotNull(config.client(2000,5000));
}

}

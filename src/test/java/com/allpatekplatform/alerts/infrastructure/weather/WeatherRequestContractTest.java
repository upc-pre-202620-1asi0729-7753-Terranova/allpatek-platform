package com.allpatekplatform.alerts.infrastructure.weather;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.http.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;
import static com.allpatekplatform.alerts.support.ClimateFixtures.*;
import java.util.List;
import com.allpatekplatform.alerts.domain.model.valueobjects.ParcelCoordinates;
import org.springframework.web.util.UriComponentsBuilder;

class WeatherRequestContractTest {

@Test void alignsCoordinatesAndDeclaresThresholdUnits() {
    var client = ForecastClientFixture.client();
    client.server().expect(request -> {
        var params = UriComponentsBuilder.fromUri(request.getURI()).build().getQueryParams();
        assertEquals("-12.0,1.0",params.getFirst("latitude"));
        assertEquals("-77.0,2.0",params.getFirst("longitude"));
        assertEquals("celsius",params.getFirst("temperature_unit"));
        assertEquals("kmh",params.getFirst("wind_speed_unit"));
        assertEquals("mm",params.getFirst("precipitation_unit"));
        assertEquals("1",params.getFirst("forecast_days"));
        assertEquals("auto",params.getFirst("timezone"));
    }).andRespond(withSuccess("["+ForecastFixtures.json()+","+ForecastFixtures.json()+"]",MediaType.APPLICATION_JSON));
    assertTrue(client.service().forecast(List.of(place(),ParcelCoordinates.fromLocation("p2","Second","1,2"))).isSuccess());
    client.server().verify();
}

}

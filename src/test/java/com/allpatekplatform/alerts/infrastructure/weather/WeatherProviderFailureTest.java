package com.allpatekplatform.alerts.infrastructure.weather;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.http.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;
import static com.allpatekplatform.alerts.support.ClimateFixtures.*;
import java.util.List;
import com.allpatekplatform.shared.application.result.*;
import java.net.SocketTimeoutException;
import java.io.IOException;
import com.allpatekplatform.shared.interfaces.rest.transform.ProblemDetailFromApplicationErrorAssembler;

class WeatherProviderFailureTest {

@Test void distinguishesProviderStatusesWithoutLeakingBodies() {
    for (int status : new int[]{400,429,500,503}) {
        var client = ForecastClientFixture.client();
        client.server().expect(method(HttpMethod.GET)).andRespond(withStatus(HttpStatusCode.valueOf(status)).body("secret provider information"));
        var result = client.service().forecast(List.of(place()));
        var error = ((Result.Failure<?,ApplicationError>)result).error();
        assertEquals(status == 400 ? "WEATHER_BAD_GATEWAY" : "WEATHER_UNAVAILABLE", error.code());
        assertNull(error.details());
        assertFalse(error.message().contains("secret"));
        assertEquals(status == 400 ? 502 : 503, ProblemDetailFromApplicationErrorAssembler.toStatusFromErrorCode(error.code()).value());
        client.server().verify();
    }
}
@Test void mapsTransportTimeoutsTo504() {
    var client = ForecastClientFixture.client();
    client.server().expect(method(HttpMethod.GET)).andRespond(withException(new SocketTimeoutException("private")));
    var result = client.service().forecast(List.of(place()));
    var error = ((Result.Failure<?,ApplicationError>)result).error();
    assertEquals(504, ProblemDetailFromApplicationErrorAssembler.toStatusFromErrorCode(error.code()).value());
    assertNull(error.details());
    client.server().verify();
}
@Test void connectionFailureAndInvalidJsonDoNotReturnSuccess() {
    var client = ForecastClientFixture.client();
    client.server().expect(method(HttpMethod.GET)).andRespond(withException(new IOException("connection refused")));
    assertEquals("WEATHER_UNAVAILABLE", ((Result.Failure<?,ApplicationError>)client.service().forecast(List.of(place()))).error().code());
    client.server().verify();
    for (String body : new String[]{"", "not-json", "{}"}) {
        var invalid = ForecastClientFixture.client();
        invalid.server().expect(method(HttpMethod.GET)).andRespond(withSuccess(body, MediaType.APPLICATION_JSON));
        assertEquals("WEATHER_BAD_GATEWAY", ((Result.Failure<?,ApplicationError>)invalid.service().forecast(List.of(place()))).error().code());
        invalid.server().verify();
    }
}

}

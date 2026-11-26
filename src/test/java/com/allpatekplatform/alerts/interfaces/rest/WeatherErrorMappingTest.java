package com.allpatekplatform.alerts.interfaces.rest;

import com.allpatekplatform.shared.application.result.ApplicationError;
import com.allpatekplatform.shared.interfaces.rest.transform.ProblemDetailFromApplicationErrorAssembler;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WeatherErrorMappingTest {
    @Test
    void mapsExpectedProviderFailuresToGatewayStatuses() {
        var unavailable = new ApplicationError("WEATHER_UNAVAILABLE", "Unavailable");
        var invalid = new ApplicationError("WEATHER_BAD_GATEWAY", "Invalid response");
        assertEquals(503, ProblemDetailFromApplicationErrorAssembler.toProblemDetailFromApplicationError(unavailable).getStatus());
        assertEquals(502, ProblemDetailFromApplicationErrorAssembler.toProblemDetailFromApplicationError(invalid).getStatus());
    }
}

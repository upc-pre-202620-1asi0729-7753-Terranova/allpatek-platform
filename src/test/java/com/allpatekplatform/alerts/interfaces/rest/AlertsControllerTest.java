package com.allpatekplatform.alerts.interfaces.rest;

import com.allpatekplatform.alerts.application.internal.outboundservices.acl.ExternalProfileService;
import com.allpatekplatform.alerts.application.queryservices.ClimateQueryService;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AlertsControllerTest {
    @Test
    void missingProfileNeverQueriesParcels() {
        var profiles = mock(ExternalProfileService.class);
        var queries = mock(ClimateQueryService.class);
        var authentication = new UsernamePasswordAuthenticationToken("farmer@example.test", null, List.of());
        when(profiles.fetchProfileByEmail("farmer@example.test")).thenReturn(Optional.empty());
        var response = new AlertsController(queries, profiles).getAlerts(null, authentication);
        assertEquals(404, response.getStatusCode().value());
        verifyNoInteractions(queries);
    }

    @Test
    void absentAuthenticationCannotProduceAnUnscopedQuery() {
        var profiles = mock(ExternalProfileService.class);
        var queries = mock(ClimateQueryService.class);
        var response = new AlertsController(queries, profiles).getAlerts(null, null);
        assertEquals(404, response.getStatusCode().value());
        verifyNoInteractions(profiles, queries);
    }
}

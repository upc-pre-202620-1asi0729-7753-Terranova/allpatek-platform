package com.allpatekplatform.alerts.interfaces.rest;

import com.allpatekplatform.alerts.application.internal.outboundservices.acl.ExternalProfileService;
import com.allpatekplatform.alerts.application.queryservices.ClimateQueryService;
import com.allpatekplatform.alerts.domain.model.queries.GetClimateAlertsQuery;
import com.allpatekplatform.shared.application.result.ApplicationError;
import com.allpatekplatform.shared.application.result.Result;
import com.allpatekplatform.shared.domain.model.valueobjects.ProfileId;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

class AlertsHttpContractTest {
    @Test
    void resolvesTheOwnerFromAuthenticationInsteadOfRequestParameters() throws Exception {
        var profiles = mock(ExternalProfileService.class);
        when(profiles.fetchProfileByEmail("farmer@example.test")).thenReturn(Optional.of(new ProfileId(7L)));
        var queries = mock(ClimateQueryService.class);
        when(queries.handle(any())).thenReturn(Result.success(List.of()));
        var mvc = MockMvcBuilders.standaloneSetup(new AlertsController(queries, profiles)).build();
        var authentication = new UsernamePasswordAuthenticationToken("farmer@example.test", null, List.of());

        mvc.perform(get("/api/v1/alerts").principal(authentication).param("ownerId", "999"))
                .andExpect(status().isOk()).andExpect(content().json("[]"));
        verify(queries).handle(new GetClimateAlertsQuery(7L));
    }

    @Test
    void mapsProviderFailureToProblemDetail() throws Exception {
        var profiles = mock(ExternalProfileService.class);
        when(profiles.fetchProfileByEmail("farmer@example.test")).thenReturn(Optional.of(new ProfileId(7L)));
        var queries = mock(ClimateQueryService.class);
        when(queries.handle(any())).thenReturn(Result.failure(new ApplicationError("WEATHER_UNAVAILABLE", "Unavailable")));
        var mvc = MockMvcBuilders.standaloneSetup(new AlertsController(queries, profiles)).build();
        var authentication = new UsernamePasswordAuthenticationToken("farmer@example.test", null, List.of());

        mvc.perform(get("/api/v1/alerts").principal(authentication))
                .andExpect(status().isServiceUnavailable())
                .andExpect(jsonPath("$.code").value("WEATHER_UNAVAILABLE"));
    }
}

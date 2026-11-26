package com.allpatekplatform.alerts.interfaces.rest;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.allpatekplatform.alerts.application.queryservices.ClimateQueryService;
import com.allpatekplatform.profiles.infrastructure.security.CurrentAccountService;
import com.allpatekplatform.profiles.domain.model.aggregates.Profile;
import com.allpatekplatform.shared.domain.model.valueobjects.ProfileId;
import com.allpatekplatform.shared.application.result.*;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;
import java.util.List;

class AlertsHttpContractTest {

@Test void returnsJsonForTheCurrentOwner() throws Exception {
    var accounts = mock(CurrentAccountService.class);
    var profile = mock(Profile.class);
    when(profile.getId()).thenReturn(new ProfileId(7L));
    when(accounts.requireProfile()).thenReturn(Result.success(profile));
    var queries = mock(ClimateQueryService.class);
    when(queries.handle(any())).thenReturn(Result.success(List.of()));
    var mvc = MockMvcBuilders.standaloneSetup(new AlertsController(queries,accounts)).build();
    mvc.perform(get("/api/v1/alerts")).andExpect(status().isOk())
            .andExpect(content().json("[]"));
    verify(queries).handle(new com.allpatekplatform.alerts.domain.model.queries.GetClimateAlertsQuery(7L));
}
@Test void mapsProviderTimeoutToProblemDetail() throws Exception {
    var accounts = mock(CurrentAccountService.class);
    var profile = mock(Profile.class);
    when(profile.getId()).thenReturn(new ProfileId(7L));
    when(accounts.requireProfile()).thenReturn(Result.success(profile));
    var queries = mock(ClimateQueryService.class);
    when(queries.handle(any())).thenReturn(Result.failure(new ApplicationError("WEATHER_TIMEOUT","Unavailable")));
    var mvc = MockMvcBuilders.standaloneSetup(new AlertsController(queries,accounts)).build();
    mvc.perform(get("/api/v1/alerts")).andExpect(status().isGatewayTimeout())
            .andExpect(jsonPath("$.code").value("WEATHER_TIMEOUT"));
}

}

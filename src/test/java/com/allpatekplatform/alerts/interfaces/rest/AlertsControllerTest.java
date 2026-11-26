package com.allpatekplatform.alerts.interfaces.rest;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.allpatekplatform.alerts.application.queryservices.ClimateQueryService;
import com.allpatekplatform.profiles.infrastructure.security.CurrentAccountService;
import com.allpatekplatform.shared.application.result.*;
import static org.mockito.Mockito.*;

class AlertsControllerTest {

@Test void missingProfileNeverQueriesParcels() {
    var accounts = mock(CurrentAccountService.class);
    var queries = mock(ClimateQueryService.class);
    when(accounts.requireProfile()).thenReturn(Result.failure(ApplicationError.notFound("PROFILE", "account")));
    var response = new AlertsController(queries, accounts).getAlerts(null);
    assertEquals(404, response.getStatusCode().value());
    verifyNoInteractions(queries);
}

}

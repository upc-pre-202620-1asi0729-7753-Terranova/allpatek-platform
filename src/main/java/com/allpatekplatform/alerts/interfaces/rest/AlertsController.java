package com.allpatekplatform.alerts.interfaces.rest;

import com.allpatekplatform.alerts.application.queryservices.ClimateQueryService;
import com.allpatekplatform.alerts.domain.model.queries.GetClimateAlertsQuery;
import com.allpatekplatform.alerts.interfaces.rest.transform.ClimateReadingResourceAssembler;
import com.allpatekplatform.profiles.infrastructure.security.CurrentAccountService;
import com.allpatekplatform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/alerts", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "alerts")
public class AlertsController {

    private final ClimateQueryService climateQueryService;
    private final CurrentAccountService currentAccountService;

    public AlertsController(
            ClimateQueryService climateQueryService,
            CurrentAccountService currentAccountService
    ) {
        this.climateQueryService = climateQueryService;
        this.currentAccountService = currentAccountService;
    }

    @GetMapping
    public ResponseEntity<?> getAlerts(
            @RequestHeader(value = "Accept-Language", required = false) String acceptLanguage
    ) {
        var profile = currentAccountService.requireProfile();
        var response = ResponseEntityAssembler.toResponseEntityFromResult(
                profile.flatMap(account -> climateQueryService.handle(new GetClimateAlertsQuery(account.getId().value())))
                        .map(ClimateReadingResourceAssembler::toResourceFromEntities),
                readings -> readings,
                HttpStatus.OK);
        return ResponseEntity.status(response.getStatusCode()).headers(response.getHeaders())
                .cacheControl(CacheControl.noStore()).body(response.getBody());
    }
}

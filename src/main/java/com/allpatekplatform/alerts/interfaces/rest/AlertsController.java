package com.allpatekplatform.alerts.interfaces.rest;

import com.allpatekplatform.alerts.application.queryservices.ClimateQueryService;
import com.allpatekplatform.alerts.domain.model.queries.GetClimateAlertsQuery;
import com.allpatekplatform.alerts.interfaces.rest.transform.ClimateReadingResourceAssembler;
import com.allpatekplatform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/alerts", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "alerts")
public class AlertsController {

    private final ClimateQueryService climateQueryService;

    public AlertsController(ClimateQueryService climateQueryService) {
        this.climateQueryService = climateQueryService;
    }

    @GetMapping
    public ResponseEntity<?> getAlerts(
            @RequestParam(required = false) Long ownerId,
            @RequestHeader(value = "Accept-Language", required = false) String acceptLanguage
    ) {
        return ResponseEntityAssembler.toResponseEntityFromResult(
                climateQueryService.handle(new GetClimateAlertsQuery(ownerId))
                        .map(ClimateReadingResourceAssembler::toResourceFromEntities),
                readings -> readings,
                HttpStatus.OK);
    }
}

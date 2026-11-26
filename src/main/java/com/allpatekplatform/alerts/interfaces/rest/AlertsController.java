package com.allpatekplatform.alerts.interfaces.rest;

import com.allpatekplatform.alerts.application.internal.outboundservices.acl.ExternalProfileService;
import com.allpatekplatform.alerts.application.queryservices.ClimateQueryService;
import com.allpatekplatform.alerts.domain.model.queries.GetClimateAlertsQuery;
import com.allpatekplatform.alerts.interfaces.rest.transform.ClimateReadingResourceAssembler;
import com.allpatekplatform.shared.application.result.ApplicationError;
import com.allpatekplatform.shared.application.result.Result;
import com.allpatekplatform.shared.domain.model.valueobjects.ProfileId;
import com.allpatekplatform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import com.allpatekplatform.alerts.interfaces.rest.resources.ClimateReadingResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(value = "/api/v1/alerts", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "alerts")
public class AlertsController {
    private final ClimateQueryService climateQueryService;
    private final ExternalProfileService externalProfileService;

    public AlertsController(ClimateQueryService climateQueryService, ExternalProfileService externalProfileService) {
        this.climateQueryService = climateQueryService;
        this.externalProfileService = externalProfileService;
    }

    @GetMapping
    @Operation(summary = "Get climate alerts", description = "Returns climate readings and alerts for the authenticated account's parcels.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Climate readings retrieved",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ClimateReadingResource.class)))),
            @ApiResponse(responseCode = "400", description = "An owned parcel has invalid coordinates"),
            @ApiResponse(responseCode = "401", description = "Authentication is required"),
            @ApiResponse(responseCode = "404", description = "The account has no profile"),
            @ApiResponse(responseCode = "502", description = "Invalid weather provider response"),
            @ApiResponse(responseCode = "503", description = "Weather provider unavailable")
    })
    public ResponseEntity<?> getAlerts(
            @RequestHeader(value = "Accept-Language", required = false) String acceptLanguage,
            Authentication authentication
    ) {
        var profileId = authentication != null && authentication.isAuthenticated()
                ? externalProfileService.fetchProfileByEmail(authentication.getName())
                : Optional.<ProfileId>empty();
        var result = profileId
                .map(id -> climateQueryService.handle(new GetClimateAlertsQuery(id.value())))
                .orElseGet(() -> Result.failure(ApplicationError.notFound("PROFILE", "current account")));

        return ResponseEntityAssembler.toResponseEntityFromResult(
                result, ClimateReadingResourceAssembler::toResourceFromEntities, HttpStatus.OK);
    }
}

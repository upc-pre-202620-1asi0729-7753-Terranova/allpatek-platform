package com.allpatekplatform.alerts.application.internal.queryservices;

import com.allpatekplatform.alerts.application.internal.outboundservices.WeatherForecastService;
import com.allpatekplatform.alerts.application.internal.outboundservices.acl.ExternalParcelService;
import com.allpatekplatform.alerts.application.queryservices.ClimateQueryService;
import com.allpatekplatform.alerts.domain.model.aggregates.ClimateReading;
import com.allpatekplatform.alerts.domain.model.queries.GetClimateAlertsQuery;
import com.allpatekplatform.alerts.domain.model.valueobjects.ParcelCoordinates;
import com.allpatekplatform.shared.application.result.ApplicationError;
import com.allpatekplatform.shared.application.result.Result;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClimateQueryServiceImpl implements ClimateQueryService {

    private final ExternalParcelService externalParcelService;
    private final WeatherForecastService weatherForecastService;

    public ClimateQueryServiceImpl(
            ExternalParcelService externalParcelService,
            WeatherForecastService weatherForecastService
    ) {
        this.externalParcelService = externalParcelService;
        this.weatherForecastService = weatherForecastService;
    }

    @Override
    public Result<List<ClimateReading>, ApplicationError> handle(GetClimateAlertsQuery query) {
        return externalParcelService.findLocations(query.ownerId()).flatMap(places ->
                places.isEmpty() ? Result.success(List.of()) : weatherForecastService.forecast(places));
    }
}

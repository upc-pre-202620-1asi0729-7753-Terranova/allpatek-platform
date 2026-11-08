package com.allpatekplatform.alerts.application.internal.queryservices;

import com.allpatekplatform.alerts.application.internal.outboundservices.WeatherForecastService;
import com.allpatekplatform.alerts.application.queryservices.ClimateQueryService;
import com.allpatekplatform.alerts.domain.model.aggregates.ClimateReading;
import com.allpatekplatform.alerts.domain.model.queries.GetClimateAlertsQuery;
import com.allpatekplatform.alerts.domain.model.valueobjects.ParcelCoordinates;
import com.allpatekplatform.parcels.interfaces.acl.ParcelsContextFacade;
import com.allpatekplatform.shared.application.result.ApplicationError;
import com.allpatekplatform.shared.application.result.Result;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ClimateQueryServiceImpl implements ClimateQueryService {

    private final ParcelsContextFacade parcelsContextFacade;
    private final WeatherForecastService weatherForecastService;

    public ClimateQueryServiceImpl(
            ParcelsContextFacade parcelsContextFacade,
            WeatherForecastService weatherForecastService
    ) {
        this.parcelsContextFacade = parcelsContextFacade;
        this.weatherForecastService = weatherForecastService;
    }

    @Override
    public Result<List<ClimateReading>, ApplicationError> handle(GetClimateAlertsQuery query) {
        List<ParcelCoordinates> places = parcelsContextFacade.findLocations(query.ownerId()).stream()
                .map(parcel -> ParcelCoordinates.fromLocation(
                        parcel.id(), parcel.name(), parcel.location()))
                .filter(Objects::nonNull)
                .toList();
        if (places.isEmpty()) {
            return Result.success(List.of());
        }
        return weatherForecastService.forecast(places);
    }
}

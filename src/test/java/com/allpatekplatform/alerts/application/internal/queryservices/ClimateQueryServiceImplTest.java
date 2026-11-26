package com.allpatekplatform.alerts.application.internal.queryservices;

import com.allpatekplatform.alerts.application.internal.outboundservices.WeatherForecastService;
import com.allpatekplatform.alerts.application.internal.outboundservices.acl.ExternalParcelService;
import com.allpatekplatform.alerts.domain.model.aggregates.ClimateReading;
import com.allpatekplatform.alerts.domain.model.queries.GetClimateAlertsQuery;
import com.allpatekplatform.alerts.domain.model.valueobjects.ParcelCoordinates;
import com.allpatekplatform.shared.application.result.ApplicationError;
import com.allpatekplatform.shared.application.result.Result;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.allpatekplatform.alerts.support.ClimateFixtures.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClimateQueryServiceImplTest {
    private final ExternalParcelService parcels = mock(ExternalParcelService.class);
    private final WeatherForecastService weather = mock(WeatherForecastService.class);
    private final ClimateQueryServiceImpl service = new ClimateQueryServiceImpl(parcels, weather);

    @Test
    void emptyPortfolioDoesNotCallWeatherProvider() {
        when(parcels.findLocations(7L)).thenReturn(Result.success(List.of()));
        assertEquals(List.of(), service.handle(new GetClimateAlertsQuery(7L)).toOptional().orElseThrow());
        verifyNoInteractions(weather);
    }

    @Test
    void invalidLocationsStopTheQueryBeforeRequestingWeather() {
        var error = ApplicationError.validationError("parcel.location", "Invalid coordinates");
        when(parcels.findLocations(7L)).thenReturn(Result.failure(error));
        assertEquals(Result.failure(error), service.handle(new GetClimateAlertsQuery(7L)));
        verifyNoInteractions(weather);
    }

    @Test
    void preservesDifferentParcelsWithTheSameCoordinates() {
        var places = List.of(place(), ParcelCoordinates.fromLocation("p2", "Second", "-12,-77"));
        var readings = places.stream().map(p -> ClimateReading.of(p, weather(0,0,0,0,0))).toList();
        when(parcels.findLocations(7L)).thenReturn(Result.success(places));
        when(weather.forecast(places)).thenReturn(Result.success(readings));
        var result = service.handle(new GetClimateAlertsQuery(7L)).toOptional().orElseThrow();
        assertEquals(List.of("p1", "p2"), result.stream().map(r -> r.place().parcelId().value()).toList());
        assertEquals("Second", result.get(1).place().name().value());
        verify(weather).forecast(places);
    }

    @Test
    void providerFailureIsReturnedWithoutInventedReadings() {
        var places = List.of(place());
        var error = new ApplicationError("WEATHER_UNAVAILABLE", "Weather unavailable");
        when(parcels.findLocations(7L)).thenReturn(Result.success(places));
        when(weather.forecast(places)).thenReturn(Result.failure(error));
        assertEquals(Result.failure(error), service.handle(new GetClimateAlertsQuery(7L)));
    }
}

package com.allpatekplatform.alerts.application.internal.outboundservices;

import com.allpatekplatform.alerts.domain.model.aggregates.ClimateReading;
import com.allpatekplatform.alerts.domain.model.valueobjects.ParcelCoordinates;
import com.allpatekplatform.shared.application.result.ApplicationError;
import com.allpatekplatform.shared.application.result.Result;

import java.util.List;

public interface WeatherForecastService {

    Result<List<ClimateReading>, ApplicationError> forecast(List<ParcelCoordinates> places);
}

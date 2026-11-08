package com.allpatekplatform.alerts.interfaces.rest.resources;

import java.util.List;

public record ClimateReadingResource(
        String parcelId,
        String parcelName,
        double latitude,
        double longitude,
        double temperature,
        double apparent,
        double humidity,
        double precipitation,
        double windSpeed,
        double cloudCover,
        int weatherCode,
        String condition,
        double tempMin,
        double tempMax,
        double precipitationSum,
        double windMax,
        List<ClimateAlertResource> alerts
) {
}

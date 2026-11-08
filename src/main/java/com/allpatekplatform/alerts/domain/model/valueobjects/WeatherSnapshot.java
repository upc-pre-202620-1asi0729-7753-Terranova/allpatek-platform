package com.allpatekplatform.alerts.domain.model.valueobjects;

public record WeatherSnapshot(
        double temperature,
        double apparent,
        double humidity,
        double precipitation,
        double windSpeed,
        double cloudCover,
        int weatherCode,
        double tempMin,
        double tempMax,
        double precipitationSum,
        double windMax
) {
}

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
    public WeatherSnapshot {
        for (double value : new double[]{temperature, apparent, humidity, precipitation, windSpeed,
                cloudCover, tempMin, tempMax, precipitationSum, windMax}) {
            if (!Double.isFinite(value)) throw new IllegalArgumentException("Weather values must be finite.");
        }
        if (humidity < 0 || humidity > 100 || cloudCover < 0 || cloudCover > 100)
            throw new IllegalArgumentException("Weather percentages must be between 0 and 100.");
        if (precipitation < 0 || precipitationSum < 0 || windSpeed < 0 || windMax < 0)
            throw new IllegalArgumentException("Rain and wind measurements cannot be negative.");
        if (tempMin > tempMax) throw new IllegalArgumentException("Minimum temperature exceeds maximum.");
    }
}

package com.allpatekplatform.alerts.interfaces.rest.resources;

public record ClimateAlertResource(
        String titleKey,
        String bodyKey,
        String levelKey,
        String tone,
        String parcelName,
        double temperature,
        double humidity,
        double precipitationSum,
        int windSpeed,
        int windMax
) {
}

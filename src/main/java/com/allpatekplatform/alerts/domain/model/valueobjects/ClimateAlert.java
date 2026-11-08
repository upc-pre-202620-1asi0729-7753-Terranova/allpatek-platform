package com.allpatekplatform.alerts.domain.model.valueobjects;

public record ClimateAlert(
        AlertMessage message,
        AlertTone tone,
        ParcelCoordinates place,
        WeatherSnapshot weather
) {

    public static ClimateAlert frost(ParcelCoordinates place, WeatherSnapshot weather) {
        return new ClimateAlert(
                new AlertMessage("alerts.frostTitle", "alerts.frostBody", "alerts.critical"),
                AlertTone.critical,
                place,
                weather);
    }

    public static ClimateAlert flood(ParcelCoordinates place, WeatherSnapshot weather) {
        boolean critical = weather.precipitationSum() >= 20;
        return new ClimateAlert(
                new AlertMessage(
                        "alerts.floodTitle",
                        "alerts.rainBody",
                        critical ? "alerts.critical" : "alerts.moderate"),
                critical ? AlertTone.critical : AlertTone.moderate,
                place,
                weather);
    }

    public static ClimateAlert wind(ParcelCoordinates place, WeatherSnapshot weather) {
        return new ClimateAlert(
                new AlertMessage("alerts.windTitle", "alerts.windBody", "alerts.moderate"),
                AlertTone.moderate,
                place,
                weather);
    }

    public static ClimateAlert ok(ParcelCoordinates place, WeatherSnapshot weather) {
        return new ClimateAlert(
                new AlertMessage("alerts.okTitle", "alerts.okBody", "alerts.normal"),
                AlertTone.normal,
                place,
                weather);
    }
}

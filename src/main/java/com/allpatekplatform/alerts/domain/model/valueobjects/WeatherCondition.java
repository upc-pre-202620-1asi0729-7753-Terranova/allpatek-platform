package com.allpatekplatform.alerts.domain.model.valueobjects;

public enum WeatherCondition {
    clear,
    cloudy,
    fog,
    rain,
    snow,
    storm;

    public static WeatherCondition fromCode(int weatherCode) {
        if (weatherCode == 0) {
            return clear;
        }
        if (weatherCode <= 3) {
            return cloudy;
        }
        if (weatherCode <= 48) {
            return fog;
        }
        if (weatherCode <= 67 || (weatherCode >= 80 && weatherCode <= 82)) {
            return rain;
        }
        if (weatherCode <= 77 || (weatherCode >= 85 && weatherCode <= 86)) {
            return snow;
        }
        return storm;
    }
}

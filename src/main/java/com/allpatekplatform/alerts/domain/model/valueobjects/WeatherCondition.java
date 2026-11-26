package com.allpatekplatform.alerts.domain.model.valueobjects;
public enum WeatherCondition {
    clear, cloudy, fog, rain, snow, storm;
    public static WeatherCondition fromCode(int code) {
        return switch (code) {
            case 0 -> clear;
            case 1, 2, 3 -> cloudy;
            case 45, 48 -> fog;
            case 51, 53, 55, 56, 57, 61, 63, 65, 66, 67, 80, 81, 82 -> rain;
            case 71, 73, 75, 77, 85, 86 -> snow;
            case 95, 96, 99 -> storm;
            default -> throw new IllegalArgumentException("Unsupported weather code.");
        };
    }
}

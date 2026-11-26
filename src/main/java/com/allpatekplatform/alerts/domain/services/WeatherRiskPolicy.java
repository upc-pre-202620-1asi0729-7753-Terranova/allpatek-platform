package com.allpatekplatform.alerts.domain.services;
import com.allpatekplatform.alerts.domain.model.valueobjects.WeatherSnapshot;
/** Existing product thresholds, in degrees Celsius, millimetres and kilometres per hour. */
public final class WeatherRiskPolicy {
    private WeatherRiskPolicy() {}
    public static boolean frost(WeatherSnapshot weather) { return weather.tempMin() <= 3; }
    public static boolean rain(WeatherSnapshot weather) { return weather.precipitationSum() >= 10 || weather.precipitation() >= 2; }
    public static boolean wind(WeatherSnapshot weather) { return weather.windSpeed() >= 30 || weather.windMax() >= 40; }
    public static boolean criticalRain(WeatherSnapshot weather) { return weather.precipitationSum() >= 20; }
}

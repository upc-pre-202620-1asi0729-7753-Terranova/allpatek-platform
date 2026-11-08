package com.allpatekplatform.alerts.domain.model.aggregates;

import com.allpatekplatform.alerts.domain.model.valueobjects.ClimateAlert;
import com.allpatekplatform.alerts.domain.model.valueobjects.ParcelCoordinates;
import com.allpatekplatform.alerts.domain.model.valueobjects.WeatherCondition;
import com.allpatekplatform.alerts.domain.model.valueobjects.WeatherSnapshot;

import java.util.ArrayList;
import java.util.List;

public record ClimateReading(
        ParcelCoordinates place,
        WeatherSnapshot weather,
        WeatherCondition condition
) {

    public static ClimateReading of(ParcelCoordinates place, WeatherSnapshot weather) {
        return new ClimateReading(place, weather, WeatherCondition.fromCode(weather.weatherCode()));
    }

    public List<ClimateAlert> alerts() {
        List<ClimateAlert> found = new ArrayList<>();
        if (weather.tempMin() <= 3) {
            found.add(ClimateAlert.frost(place, weather));
        }
        if (weather.precipitationSum() >= 10 || weather.precipitation() >= 2) {
            found.add(ClimateAlert.flood(place, weather));
        }
        if (weather.windSpeed() >= 30 || weather.windMax() >= 40) {
            found.add(ClimateAlert.wind(place, weather));
        }
        if (found.isEmpty()) {
            found.add(ClimateAlert.ok(place, weather));
        }
        return List.copyOf(found);
    }
}

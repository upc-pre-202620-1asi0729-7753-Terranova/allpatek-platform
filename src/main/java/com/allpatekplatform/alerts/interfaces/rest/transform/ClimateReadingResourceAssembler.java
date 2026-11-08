package com.allpatekplatform.alerts.interfaces.rest.transform;

import com.allpatekplatform.alerts.domain.model.aggregates.ClimateReading;
import com.allpatekplatform.alerts.domain.model.valueobjects.ClimateAlert;
import com.allpatekplatform.alerts.interfaces.rest.resources.ClimateAlertResource;
import com.allpatekplatform.alerts.interfaces.rest.resources.ClimateReadingResource;

import java.util.List;

public final class ClimateReadingResourceAssembler {

    private ClimateReadingResourceAssembler() {
    }

    public static ClimateReadingResource toResourceFromEntity(ClimateReading reading) {
        return new ClimateReadingResource(
                reading.place().parcelId().value(),
                reading.place().name().value(),
                reading.place().latitude(),
                reading.place().longitude(),
                reading.weather().temperature(),
                reading.weather().apparent(),
                reading.weather().humidity(),
                reading.weather().precipitation(),
                reading.weather().windSpeed(),
                reading.weather().cloudCover(),
                reading.weather().weatherCode(),
                reading.condition().name(),
                reading.weather().tempMin(),
                reading.weather().tempMax(),
                reading.weather().precipitationSum(),
                reading.weather().windMax(),
                reading.alerts().stream().map(ClimateReadingResourceAssembler::toAlert).toList());
    }

    private static ClimateAlertResource toAlert(ClimateAlert alert) {
        return new ClimateAlertResource(
                alert.message().titleKey(),
                alert.message().bodyKey(),
                alert.message().levelKey(),
                alert.tone().name(),
                alert.place().name().value(),
                alert.weather().temperature(),
                alert.weather().humidity(),
                alert.weather().precipitationSum(),
                (int) Math.round(alert.weather().windSpeed()),
                (int) Math.round(alert.weather().windMax()));
    }

    public static List<ClimateReadingResource> toResourceFromEntities(List<ClimateReading> readings) {
        return readings.stream().map(ClimateReadingResourceAssembler::toResourceFromEntity).toList();
    }
}

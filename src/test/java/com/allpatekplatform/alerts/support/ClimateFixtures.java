package com.allpatekplatform.alerts.support;
import com.allpatekplatform.alerts.domain.model.valueobjects.*;
public final class ClimateFixtures {
    private ClimateFixtures() {}
    public static ParcelCoordinates place() { return ParcelCoordinates.fromLocation("p1", "Farm", "-12,-77"); }
    public static WeatherSnapshot weather(double min, double rain, double sum, double wind, double gust) {
        return new WeatherSnapshot(18, 18, 50, rain, wind, 50, 0, min, 25, sum, gust);
    }
}

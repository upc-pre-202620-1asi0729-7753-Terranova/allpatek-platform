package com.allpatekplatform.alerts.domain.model.valueobjects;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class WeatherConditionTest {

@Test void mapsAllSupportedCodeGroups() {
    assertEquals(WeatherCondition.clear, WeatherCondition.fromCode(0));
    for (int code : new int[]{1,2,3}) assertEquals(WeatherCondition.cloudy, WeatherCondition.fromCode(code));
    for (int code : new int[]{45,48}) assertEquals(WeatherCondition.fog, WeatherCondition.fromCode(code));
    for (int code : new int[]{51,53,55,56,57,61,63,65,66,67,80,81,82}) assertEquals(WeatherCondition.rain, WeatherCondition.fromCode(code));
    for (int code : new int[]{71,73,75,77,85,86}) assertEquals(WeatherCondition.snow, WeatherCondition.fromCode(code));
    for (int code : new int[]{95,96,99}) assertEquals(WeatherCondition.storm, WeatherCondition.fromCode(code));
}
@Test void rejectsUnknownCodesRatherThanInventingStorms() {
    for (int code : new int[]{-1,4,49,68,78,84,87,100})
        assertThrows(IllegalArgumentException.class, () -> WeatherCondition.fromCode(code));
}

}

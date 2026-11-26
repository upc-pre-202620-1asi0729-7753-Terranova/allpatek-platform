package com.allpatekplatform.alerts.domain.model.valueobjects;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class GeoPointTest {

@Test void rejectsNonfiniteAndOutOfRangeCoordinates() {
    for (double value : new double[]{Double.NaN, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY, 91, -91})
        assertThrows(IllegalArgumentException.class, () -> new GeoPoint(value, 0));
    for (double value : new double[]{Double.NaN, 181, -181})
        assertThrows(IllegalArgumentException.class, () -> new GeoPoint(0, value));
}
@Test void acceptsBoundaryAndEquatorialCoordinates() {
    assertDoesNotThrow(() -> new GeoPoint(90, 180));
    assertDoesNotThrow(() -> new GeoPoint(-90, -180));
    assertEquals(0, new GeoPoint(0, 0).latitude());
}

}

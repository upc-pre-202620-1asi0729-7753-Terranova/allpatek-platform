package com.allpatekplatform.alerts.domain.model.valueobjects;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class ParcelCoordinatesTest {

@Test void rejectsMalformedLocations() {
    for (String value : new String[]{null, "", "10", "10,", ",20", "10,20,30", "NaN,20", "91,0", "north,east"})
        assertThrows(IllegalArgumentException.class, () -> ParcelCoordinates.fromLocation("p1", "Farm", value));
}
@Test void parsesSignedAndWhitespaceCoordinates() {
    var point = ParcelCoordinates.fromLocation("p1", "Farm", " -12.1 , -77.2 ");
    assertEquals(-12.1, point.latitude());
    assertEquals(-77.2, point.longitude());
}

}

package com.allpatekplatform.alerts.domain.model.queries;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class GetClimateAlertsQueryTest {

@Test void cannotConstructUnscopedQuery() {
    for (Long id : new Long[]{null, 0L, -1L})
        assertThrows(IllegalArgumentException.class, () -> new GetClimateAlertsQuery(id));
    assertEquals(42L, new GetClimateAlertsQuery(42L).ownerId());
}

}

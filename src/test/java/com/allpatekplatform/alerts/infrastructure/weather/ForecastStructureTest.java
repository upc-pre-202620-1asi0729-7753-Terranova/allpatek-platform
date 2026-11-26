package com.allpatekplatform.alerts.infrastructure.weather;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import static com.allpatekplatform.alerts.support.ClimateFixtures.*;

class ForecastStructureTest {

@Test void refusesProviderErrorsAndInvalidRoots() throws Exception {
    for (String json : new String[]{"null","42","true","[]","[null]","{}","{\"error\":true,\"reason\":\"private provider detail\"}","{\"current\":[],\"daily\":{}}"}) {
        var root = new ObjectMapper().readTree(json);
        assertThrows(IllegalArgumentException.class, () -> OpenMeteoResponseParser.blocks(root,1));
    }
}

}

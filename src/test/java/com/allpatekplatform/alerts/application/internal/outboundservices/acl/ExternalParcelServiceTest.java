package com.allpatekplatform.alerts.application.internal.outboundservices.acl;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.allpatekplatform.parcels.interfaces.acl.ParcelsContextFacade;
import com.allpatekplatform.parcels.interfaces.acl.ParcelLocation;
import com.allpatekplatform.shared.application.result.*;
import java.util.List;
import static org.mockito.Mockito.*;

class ExternalParcelServiceTest {

@Test void malformedParcelCannotDisappearFromAValidResult() {
    var facade = mock(ParcelsContextFacade.class);
    when(facade.findLocations(7L)).thenReturn(List.of(
            new ParcelLocation("good", "Good", "0,0"),
            new ParcelLocation("bad", "Bad", "invalid")));
    var result = new ExternalParcelService(facade).findLocations(7L);
    assertTrue(result.isFailure());
    assertTrue(((Result.Failure<?, ApplicationError>) result).error().details().contains("bad"));
}
@Test void emptyOwnerPortfolioIsSuccessful() {
    var facade = mock(ParcelsContextFacade.class);
    when(facade.findLocations(7L)).thenReturn(List.of());
    assertTrue(new ExternalParcelService(facade).findLocations(7L).toOptional().orElseThrow().isEmpty());
}

}

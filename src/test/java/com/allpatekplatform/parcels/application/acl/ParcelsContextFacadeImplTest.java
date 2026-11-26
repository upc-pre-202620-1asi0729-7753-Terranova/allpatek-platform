package com.allpatekplatform.parcels.application.acl;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.allpatekplatform.parcels.domain.repositories.ParcelRepository;
import java.util.List;
import static org.mockito.Mockito.*;

class ParcelsContextFacadeImplTest {

@Test void rejectsMissingOwnerBeforeRepositoryAccess() {
    var repository = mock(ParcelRepository.class);
    var facade = new ParcelsContextFacadeImpl(repository);
    assertThrows(IllegalArgumentException.class, () -> facade.findLocations(null));
    assertThrows(IllegalArgumentException.class, () -> facade.findLocations(0L));
    verifyNoInteractions(repository);
}
@Test void onlyQueriesRequestedOwner() {
    var repository = mock(ParcelRepository.class);
    when(repository.findByOwnerId(7L)).thenReturn(List.of());
    assertTrue(new ParcelsContextFacadeImpl(repository).findLocations(7L).isEmpty());
    verify(repository).findByOwnerId(7L);
    verify(repository, never()).findAll();
}

}

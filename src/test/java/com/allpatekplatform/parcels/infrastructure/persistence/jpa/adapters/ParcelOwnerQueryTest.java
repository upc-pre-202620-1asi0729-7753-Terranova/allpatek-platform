package com.allpatekplatform.parcels.infrastructure.persistence.jpa.adapters;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.allpatekplatform.parcels.infrastructure.persistence.jpa.repositories.ParcelPersistenceRepository;
import java.util.List;
import static org.mockito.Mockito.*;

class ParcelOwnerQueryTest {

@Test void delegatesOwnerPredicateToPersistence() {
    var persistence = mock(ParcelPersistenceRepository.class);
    when(persistence.findByOwnerId(9L)).thenReturn(List.of());
    assertTrue(new ParcelRepositoryImpl(persistence).findByOwnerId(9L).isEmpty());
    verify(persistence).findByOwnerId(9L);
    verify(persistence, never()).findAll();
}

}

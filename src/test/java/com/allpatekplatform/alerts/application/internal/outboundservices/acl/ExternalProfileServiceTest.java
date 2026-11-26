package com.allpatekplatform.alerts.application.internal.outboundservices.acl;

import com.allpatekplatform.profiles.interfaces.acl.ProfilesContextFacade;
import com.allpatekplatform.shared.domain.model.valueobjects.ProfileId;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ExternalProfileServiceTest {
    @Test
    void translatesThePublishedProfileIdentifier() {
        var facade = mock(ProfilesContextFacade.class);
        when(facade.fetchProfileIdByEmail("farmer@example.test")).thenReturn(7L);
        var service = new ExternalProfileService(facade);
        assertEquals(Optional.of(new ProfileId(7L)), service.fetchProfileByEmail("farmer@example.test"));
        verify(facade).fetchProfileIdByEmail("farmer@example.test");
    }

    @Test
    void missingProfileCannotBecomeAnOwnerIdentifier() {
        for (Long id : new Long[]{0L, -1L, null}) {
            var facade = mock(ProfilesContextFacade.class);
            when(facade.fetchProfileIdByEmail("farmer@example.test")).thenReturn(id);
            assertTrue(new ExternalProfileService(facade).fetchProfileByEmail("farmer@example.test").isEmpty());
        }
    }

    @Test
    void absentEmailDoesNotQueryTheProfilesContext() {
        var facade = mock(ProfilesContextFacade.class);
        var service = new ExternalProfileService(facade);
        assertTrue(service.fetchProfileByEmail(null).isEmpty());
        assertTrue(service.fetchProfileByEmail("   ").isEmpty());
        verifyNoInteractions(facade);
    }
}

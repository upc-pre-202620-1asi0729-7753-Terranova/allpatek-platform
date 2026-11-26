package com.allpatekplatform.alerts.integration;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import com.allpatekplatform.parcels.infrastructure.persistence.jpa.entities.ParcelPersistenceEntity;
import com.allpatekplatform.parcels.infrastructure.persistence.jpa.repositories.ParcelPersistenceRepository;
import com.allpatekplatform.parcels.interfaces.acl.ParcelsContextFacade;
import com.allpatekplatform.parcels.domain.model.valueobjects.ParcelStatus;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class AlertsSecurityAndPersistenceTest {

@Autowired WebApplicationContext context;
@Autowired ParcelPersistenceRepository repository;
@Autowired ParcelsContextFacade facade;
@Test void anonymousRequestsAreRejectedByTheSecurityChain() throws Exception {
    var mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    mvc.perform(get("/api/v1/alerts")).andExpect(status().isUnauthorized());
}
@Test void authenticatedAccountWithoutProfileCannotAccessOtherOwnersParcels() throws Exception {
    save("private-parcel",700L);
    var mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    mvc.perform(get("/api/v1/alerts").with(user("missing-profile@example.test").roles("FARMER")))
        .andExpect(status().isNotFound()).andExpect(jsonPath("$.code").value("PROFILE_NOT_FOUND"));
}
@Test void databaseOwnerPredicateExcludesOtherOwners() {
    save("owner-a",701L);
    save("owner-b",702L);
    repository.flush();
    assertEquals(java.util.List.of("owner-a"),repository.findByOwnerId(701L).stream().map(ParcelPersistenceEntity::getId).toList());
    assertEquals(java.util.List.of("owner-a"),facade.findLocations(701L).stream().map(p->p.id()).toList());
    assertTrue(facade.findLocations(999L).isEmpty());
}
private void save(String id, Long owner) {
    var parcel = new ParcelPersistenceEntity();
    parcel.setId(id); parcel.setOwnerId(owner); parcel.setName(id);
    parcel.setStatus(ParcelStatus.available); parcel.setLocation("-12,-77");
    repository.saveAndFlush(parcel);
}

}

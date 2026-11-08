package com.allpatekplatform.shared.infrastructure.persistence.jpa.assemblers;

import com.allpatekplatform.shared.domain.model.aggregates.Profile;
import com.allpatekplatform.shared.domain.model.valueobjects.CompanyDetails;
import com.allpatekplatform.shared.domain.model.valueobjects.IdentityDocument;
import com.allpatekplatform.shared.domain.model.valueobjects.PersonName;
import com.allpatekplatform.shared.domain.model.valueobjects.PhoneNumber;
import com.allpatekplatform.shared.domain.model.valueobjects.ProfileId;
import com.allpatekplatform.shared.domain.model.valueobjects.StreetAddress;
import com.allpatekplatform.shared.infrastructure.persistence.jpa.embeddables.CompanyDetailsPersistenceEmbeddable;
import com.allpatekplatform.shared.infrastructure.persistence.jpa.entities.ProfilePersistenceEntity;

/**
 * Static assembler between profile domain and persistence representations.
 */
public final class ProfilePersistenceAssembler {

    private ProfilePersistenceAssembler() {
    }

    public static Profile toDomainFromPersistence(ProfilePersistenceEntity entity) {
        return new Profile(
                new ProfileId(entity.getId()),
                new PersonName(entity.getFullName()),
                new PhoneNumber(entity.getPhone()),
                new IdentityDocument(entity.getDocument()),
                new StreetAddress(entity.getAddress()),
                entity.getEmailAddress(),
                toDomainFromPersistence(entity.getCompanyDetails()));
    }

    public static ProfilePersistenceEntity toPersistenceFromDomain(Profile profile) {
        ProfilePersistenceEntity entity = new ProfilePersistenceEntity();
        entity.setId(profile.getId().value());
        entity.setFullName(profile.getFullName().value());
        entity.setPhone(profile.getPhone().value());
        entity.setDocument(profile.getDocument().value());
        entity.setAddress(profile.getAddress().value());
        entity.setEmailAddress(profile.getEmailAddress());
        entity.setCompanyDetails(toPersistenceFromDomain(profile.getCompany()));
        return entity;
    }

    private static CompanyDetails toDomainFromPersistence(CompanyDetailsPersistenceEmbeddable value) {
        return value == null ? new CompanyDetails("", "", "") : new CompanyDetails(value.getCompany(), value.getRuc(), value.getActivity());
    }

    private static CompanyDetailsPersistenceEmbeddable toPersistenceFromDomain(CompanyDetails value) {
        return value == null ? null : new CompanyDetailsPersistenceEmbeddable(value.company(), value.ruc(), value.activity());
    }
}

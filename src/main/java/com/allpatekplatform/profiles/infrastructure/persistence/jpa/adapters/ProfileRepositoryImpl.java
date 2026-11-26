package com.allpatekplatform.profiles.infrastructure.persistence.jpa.adapters;

import com.allpatekplatform.profiles.domain.model.aggregates.Profile;
import com.allpatekplatform.profiles.domain.model.events.ProfileCreatedEvent;
import com.allpatekplatform.profiles.domain.model.valueobjects.EmailAddress;
import com.allpatekplatform.profiles.domain.repositories.ProfileRepository;
import com.allpatekplatform.profiles.infrastructure.persistence.jpa.assemblers.ProfilePersistenceAssembler;
import com.allpatekplatform.profiles.infrastructure.persistence.jpa.repositories.ProfilePersistenceRepository;
import com.allpatekplatform.shared.infrastructure.events.DomainEventPublisher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository adapter that bridges the domain profile repository port with Spring Data JPA.
 *
 * <p>Also acts as the event-publishing boundary: after a brand-new {@link Profile} is
 * persisted (and the JPA-assigned id is therefore available), a {@link ProfileCreatedEvent}
 * is dispatched via {@link DomainEventPublisher}.</p>
 */
@Repository
public class ProfileRepositoryImpl implements ProfileRepository {

    private final ProfilePersistenceRepository persistenceRepository;
    private final DomainEventPublisher domainEventPublisher;

    public ProfileRepositoryImpl(
            ProfilePersistenceRepository persistenceRepository,
            DomainEventPublisher domainEventPublisher
    ) {
        this.persistenceRepository = persistenceRepository;
        this.domainEventPublisher = domainEventPublisher;
    }

    @Override
    public Profile save(Profile profile) {
        boolean isNew = profile.getId() == null || profile.getId().value() == null;
        var saved = persistenceRepository.save(ProfilePersistenceAssembler.toPersistenceFromDomain(profile));
        var savedProfile = ProfilePersistenceAssembler.toDomainFromPersistence(saved);
        if (isNew) {
            savedProfile.onCreated();
            domainEventPublisher.publishAndClearEvents(savedProfile);
        }
        return savedProfile;
    }

    @Override
    public Optional<Profile> findById(Long id) {
        return persistenceRepository.findById(id).map(ProfilePersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public Optional<Profile> findByEmailAddress(EmailAddress emailAddress) {
        return persistenceRepository.findByEmailAddress(emailAddress)
                .map(ProfilePersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<Profile> findAll() {
        return persistenceRepository.findAll().stream()
                .map(ProfilePersistenceAssembler::toDomainFromPersistence)
                .toList();
    }
}

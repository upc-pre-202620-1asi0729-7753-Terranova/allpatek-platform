package com.allpatekplatform.shared.infrastructure.persistence.jpa.adapters;

import com.allpatekplatform.shared.domain.model.aggregates.Profile;
import com.allpatekplatform.shared.domain.model.valueobjects.EmailAddress;
import com.allpatekplatform.shared.domain.repositories.ProfileRepository;
import com.allpatekplatform.shared.infrastructure.persistence.jpa.assemblers.ProfilePersistenceAssembler;
import com.allpatekplatform.shared.infrastructure.persistence.jpa.repositories.ProfilePersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProfileRepositoryImpl implements ProfileRepository {

    private final ProfilePersistenceRepository persistenceRepository;

    public ProfileRepositoryImpl(ProfilePersistenceRepository persistenceRepository) {
        this.persistenceRepository = persistenceRepository;
    }

    @Override
    public Profile save(Profile profile) {
        var saved = persistenceRepository.save(ProfilePersistenceAssembler.toPersistenceFromDomain(profile));
        return ProfilePersistenceAssembler.toDomainFromPersistence(saved);
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

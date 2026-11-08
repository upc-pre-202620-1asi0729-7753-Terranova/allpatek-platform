package com.allpatekplatform.shared.infrastructure.persistence.jpa.repositories;

import com.allpatekplatform.shared.domain.model.valueobjects.EmailAddress;
import com.allpatekplatform.shared.infrastructure.persistence.jpa.entities.ProfilePersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfilePersistenceRepository extends JpaRepository<ProfilePersistenceEntity, Long> {

    Optional<ProfilePersistenceEntity> findByEmailAddress(EmailAddress emailAddress);
}

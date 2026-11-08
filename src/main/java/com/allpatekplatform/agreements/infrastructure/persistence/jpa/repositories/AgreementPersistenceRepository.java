package com.allpatekplatform.agreements.infrastructure.persistence.jpa.repositories;

import com.allpatekplatform.agreements.infrastructure.persistence.jpa.entities.AgreementPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgreementPersistenceRepository extends JpaRepository<AgreementPersistenceEntity, String> {
}

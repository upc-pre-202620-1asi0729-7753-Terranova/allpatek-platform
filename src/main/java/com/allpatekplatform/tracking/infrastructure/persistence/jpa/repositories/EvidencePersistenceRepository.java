package com.allpatekplatform.tracking.infrastructure.persistence.jpa.repositories;

import com.allpatekplatform.tracking.infrastructure.persistence.jpa.entities.EvidencePersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EvidencePersistenceRepository extends JpaRepository<EvidencePersistenceEntity, String> {
}

package com.allpatekplatform.parcels.infrastructure.persistence.jpa.repositories;

import com.allpatekplatform.parcels.infrastructure.persistence.jpa.entities.ParcelPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParcelPersistenceRepository extends JpaRepository<ParcelPersistenceEntity, String> {
}

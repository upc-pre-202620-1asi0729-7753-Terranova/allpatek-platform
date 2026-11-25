package com.allpatekplatform.escrow.infrastructure.persistence.jpa.repositories;

import com.allpatekplatform.escrow.infrastructure.persistence.jpa.entities.WalletPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WalletPersistenceRepository extends JpaRepository<WalletPersistenceEntity, String> {

    Optional<WalletPersistenceEntity> findFirstByProfileId(Long profileId);
}

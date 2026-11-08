package com.allpatekplatform.escrow.infrastructure.persistence.jpa.repositories;

import com.allpatekplatform.escrow.infrastructure.persistence.jpa.entities.WalletPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletPersistenceRepository extends JpaRepository<WalletPersistenceEntity, String> {
}

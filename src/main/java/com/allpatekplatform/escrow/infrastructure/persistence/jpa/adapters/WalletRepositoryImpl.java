package com.allpatekplatform.escrow.infrastructure.persistence.jpa.adapters;

import com.allpatekplatform.escrow.domain.model.aggregates.Wallet;
import com.allpatekplatform.escrow.domain.repositories.WalletRepository;
import com.allpatekplatform.escrow.infrastructure.persistence.jpa.assemblers.WalletPersistenceAssembler;
import com.allpatekplatform.escrow.infrastructure.persistence.jpa.repositories.WalletPersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WalletRepositoryImpl implements WalletRepository {

    private final WalletPersistenceRepository persistenceRepository;

    public WalletRepositoryImpl(WalletPersistenceRepository persistenceRepository) {
        this.persistenceRepository = persistenceRepository;
    }

    @Override
    public Wallet save(Wallet wallet) {
        return WalletPersistenceAssembler.toDomainFromPersistence(
                persistenceRepository.save(WalletPersistenceAssembler.toPersistenceFromDomain(wallet)));
    }

    @Override
    public boolean existsById(String id) {
        return persistenceRepository.existsById(id);
    }

    @Override
    public List<Wallet> findAll() {
        return persistenceRepository.findAll().stream()
                .map(WalletPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public void deleteById(String id) {
        persistenceRepository.deleteById(id);
    }
}

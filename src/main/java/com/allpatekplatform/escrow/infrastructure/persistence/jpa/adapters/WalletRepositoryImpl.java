package com.allpatekplatform.escrow.infrastructure.persistence.jpa.adapters;

import com.allpatekplatform.escrow.domain.model.aggregates.Wallet;
import com.allpatekplatform.escrow.domain.repositories.WalletRepository;
import com.allpatekplatform.escrow.infrastructure.persistence.jpa.assemblers.WalletPersistenceAssembler;
import com.allpatekplatform.escrow.infrastructure.persistence.jpa.repositories.WalletPersistenceRepository;
import com.allpatekplatform.shared.infrastructure.events.DomainEventPublisher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class WalletRepositoryImpl implements WalletRepository {

    private final WalletPersistenceRepository persistenceRepository;
    private final DomainEventPublisher domainEventPublisher;

    public WalletRepositoryImpl(
            WalletPersistenceRepository persistenceRepository,
            DomainEventPublisher domainEventPublisher
    ) {
        this.persistenceRepository = persistenceRepository;
        this.domainEventPublisher = domainEventPublisher;
    }

    @Override
    public Wallet save(Wallet wallet) {
        var saved = WalletPersistenceAssembler.toDomainFromPersistence(
                persistenceRepository.save(WalletPersistenceAssembler.toPersistenceFromDomain(wallet)));
        domainEventPublisher.publishAndClearEvents(saved);
        return saved;
    }

    @Override
    public boolean existsById(String id) {
        return persistenceRepository.existsById(id);
    }

    @Override
    public Optional<Wallet> findByProfileId(Long profileId) {
        return persistenceRepository.findFirstByProfileId(profileId)
                .map(WalletPersistenceAssembler::toDomainFromPersistence);
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

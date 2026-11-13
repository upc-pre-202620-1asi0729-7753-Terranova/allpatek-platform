package com.allpatekplatform.agreements.infrastructure.persistence.jpa.adapters;

import com.allpatekplatform.agreements.domain.model.aggregates.Agreement;
import com.allpatekplatform.agreements.domain.repositories.AgreementRepository;
import com.allpatekplatform.agreements.infrastructure.persistence.jpa.assemblers.AgreementPersistenceAssembler;
import com.allpatekplatform.agreements.infrastructure.persistence.jpa.repositories.AgreementPersistenceRepository;
import com.allpatekplatform.shared.infrastructure.events.DomainEventPublisher;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AgreementRepositoryImpl implements AgreementRepository {

    private final AgreementPersistenceRepository persistenceRepository;
    private final DomainEventPublisher domainEventPublisher;

    public AgreementRepositoryImpl(
            AgreementPersistenceRepository persistenceRepository,
            DomainEventPublisher domainEventPublisher
    ) {
        this.persistenceRepository = persistenceRepository;
        this.domainEventPublisher = domainEventPublisher;
    }

    @Override
    public Agreement save(Agreement agreement) {
        boolean alreadyComplete = persistenceRepository.findById(agreement.getId().value())
                .map(entity -> {
                    var signatures = entity.getSignatures();
                    return signatures != null
                            && signatures.isMerchantSigned()
                            && signatures.isFarmerSigned();
                })
                .orElse(false);
        var saved = AgreementPersistenceAssembler.toDomainFromPersistence(
                persistenceRepository.save(AgreementPersistenceAssembler.toPersistenceFromDomain(agreement)));
        if (saved.isFullySigned() && !alreadyComplete) {
            saved.onFullySigned();
            domainEventPublisher.publishAndClearEvents(saved);
        }
        return saved;
    }

    @Override
    public boolean existsById(String id) {
        return persistenceRepository.existsById(id);
    }

    @Override
    public List<Agreement> findAll() {
        return persistenceRepository.findAll().stream()
                .map(AgreementPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public void deleteById(String id) {
        persistenceRepository.deleteById(id);
    }
}

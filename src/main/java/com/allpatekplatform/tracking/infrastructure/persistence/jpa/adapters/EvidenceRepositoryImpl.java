package com.allpatekplatform.tracking.infrastructure.persistence.jpa.adapters;

import com.allpatekplatform.shared.infrastructure.events.DomainEventPublisher;
import com.allpatekplatform.tracking.domain.model.aggregates.MilestoneEvidence;
import com.allpatekplatform.tracking.domain.repositories.EvidenceRepository;
import com.allpatekplatform.tracking.infrastructure.persistence.jpa.assemblers.EvidencePersistenceAssembler;
import com.allpatekplatform.tracking.infrastructure.persistence.jpa.repositories.EvidencePersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class EvidenceRepositoryImpl implements EvidenceRepository {

    private final EvidencePersistenceRepository persistenceRepository;
    private final DomainEventPublisher domainEventPublisher;

    public EvidenceRepositoryImpl(
            EvidencePersistenceRepository persistenceRepository,
            DomainEventPublisher domainEventPublisher
    ) {
        this.persistenceRepository = persistenceRepository;
        this.domainEventPublisher = domainEventPublisher;
    }

    @Override
    public MilestoneEvidence save(MilestoneEvidence evidence) {
        var saved = EvidencePersistenceAssembler.toDomainFromPersistence(
                persistenceRepository.save(EvidencePersistenceAssembler.toPersistenceFromDomain(evidence)));
        domainEventPublisher.publishAndClearEvents(evidence);
        return saved;
    }

    @Override
    public Optional<MilestoneEvidence> findById(String id) {
        return persistenceRepository.findById(id).map(EvidencePersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public boolean existsById(String id) {
        return persistenceRepository.existsById(id);
    }

    @Override
    public List<MilestoneEvidence> findAll() {
        return persistenceRepository.findAll().stream()
                .map(EvidencePersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public void deleteById(String id) {
        persistenceRepository.deleteById(id);
    }
}

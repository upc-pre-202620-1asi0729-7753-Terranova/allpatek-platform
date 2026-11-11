package com.allpatekplatform.tracking.infrastructure.persistence.jpa.adapters;

import com.allpatekplatform.tracking.domain.model.aggregates.MilestoneEvidence;
import com.allpatekplatform.tracking.domain.repositories.EvidenceRepository;
import com.allpatekplatform.tracking.infrastructure.persistence.jpa.assemblers.EvidencePersistenceAssembler;
import com.allpatekplatform.tracking.infrastructure.persistence.jpa.repositories.EvidencePersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EvidenceRepositoryImpl implements EvidenceRepository {

    private final EvidencePersistenceRepository persistenceRepository;

    public EvidenceRepositoryImpl(EvidencePersistenceRepository persistenceRepository) {
        this.persistenceRepository = persistenceRepository;
    }

    @Override
    public MilestoneEvidence save(MilestoneEvidence evidence) {
        return EvidencePersistenceAssembler.toDomainFromPersistence(
                persistenceRepository.save(EvidencePersistenceAssembler.toPersistenceFromDomain(evidence)));
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

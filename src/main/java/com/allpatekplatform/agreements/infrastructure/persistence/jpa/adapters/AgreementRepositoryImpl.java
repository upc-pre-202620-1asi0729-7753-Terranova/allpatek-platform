package com.allpatekplatform.agreements.infrastructure.persistence.jpa.adapters;

import com.allpatekplatform.agreements.domain.model.aggregates.Agreement;
import com.allpatekplatform.agreements.domain.repositories.AgreementRepository;
import com.allpatekplatform.agreements.infrastructure.persistence.jpa.assemblers.AgreementPersistenceAssembler;
import com.allpatekplatform.agreements.infrastructure.persistence.jpa.repositories.AgreementPersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AgreementRepositoryImpl implements AgreementRepository {

    private final AgreementPersistenceRepository persistenceRepository;

    public AgreementRepositoryImpl(AgreementPersistenceRepository persistenceRepository) {
        this.persistenceRepository = persistenceRepository;
    }

    @Override
    public Agreement save(Agreement agreement) {
        return AgreementPersistenceAssembler.toDomainFromPersistence(
                persistenceRepository.save(AgreementPersistenceAssembler.toPersistenceFromDomain(agreement)));
    }

    @Override
    public List<Agreement> findAll() {
        return persistenceRepository.findAll().stream()
                .map(AgreementPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }
}

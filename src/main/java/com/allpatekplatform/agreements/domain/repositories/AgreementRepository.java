package com.allpatekplatform.agreements.domain.repositories;

import com.allpatekplatform.agreements.domain.model.aggregates.Agreement;

import java.util.List;
import java.util.Optional;

public interface AgreementRepository {

    Agreement save(Agreement agreement);

    Optional<Agreement> findById(String id);

    boolean existsById(String id);

    List<Agreement> findAll();

    void deleteById(String id);
}

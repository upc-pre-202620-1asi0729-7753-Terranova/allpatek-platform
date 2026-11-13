package com.allpatekplatform.agreements.domain.repositories;

import com.allpatekplatform.agreements.domain.model.aggregates.Agreement;

import java.util.List;

public interface AgreementRepository {

    Agreement save(Agreement agreement);

    boolean existsById(String id);

    List<Agreement> findAll();

    void deleteById(String id);
}

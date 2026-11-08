package com.allpatekplatform.agreements.domain.repositories;

import com.allpatekplatform.agreements.domain.model.aggregates.Agreement;

import java.util.List;

public interface AgreementRepository {

    Agreement save(Agreement agreement);

    List<Agreement> findAll();
}

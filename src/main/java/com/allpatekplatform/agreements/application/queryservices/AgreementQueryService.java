package com.allpatekplatform.agreements.application.queryservices;

import com.allpatekplatform.agreements.domain.model.aggregates.Agreement;
import com.allpatekplatform.agreements.domain.model.queries.GetAgreementByIdQuery;
import com.allpatekplatform.agreements.domain.model.queries.GetAllAgreementsQuery;

import java.util.List;
import java.util.Optional;

public interface AgreementQueryService {

    List<Agreement> handle(GetAllAgreementsQuery query);

    Optional<Agreement> handle(GetAgreementByIdQuery query);
}

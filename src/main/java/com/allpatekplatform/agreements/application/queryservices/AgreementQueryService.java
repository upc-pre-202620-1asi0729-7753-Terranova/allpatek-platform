package com.allpatekplatform.agreements.application.queryservices;

import com.allpatekplatform.agreements.domain.model.aggregates.Agreement;
import com.allpatekplatform.agreements.domain.model.queries.GetAllAgreementsQuery;

import java.util.List;

public interface AgreementQueryService {

    List<Agreement> handle(GetAllAgreementsQuery query);
}

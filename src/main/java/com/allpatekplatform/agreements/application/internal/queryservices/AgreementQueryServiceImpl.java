package com.allpatekplatform.agreements.application.internal.queryservices;

import com.allpatekplatform.agreements.application.queryservices.AgreementQueryService;
import com.allpatekplatform.agreements.domain.model.aggregates.Agreement;
import com.allpatekplatform.agreements.domain.model.queries.GetAllAgreementsQuery;
import com.allpatekplatform.agreements.domain.repositories.AgreementRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AgreementQueryServiceImpl implements AgreementQueryService {

    private final AgreementRepository agreementRepository;

    public AgreementQueryServiceImpl(AgreementRepository agreementRepository) {
        this.agreementRepository = agreementRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Agreement> handle(GetAllAgreementsQuery query) {
        return agreementRepository.findAll();
    }
}

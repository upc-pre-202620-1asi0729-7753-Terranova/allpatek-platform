package com.allpatekplatform.agreements.application.internal.commandservices;

import com.allpatekplatform.agreements.application.commandservices.AgreementCommandService;
import com.allpatekplatform.agreements.domain.model.aggregates.Agreement;
import com.allpatekplatform.agreements.domain.model.commands.SaveAgreementCommand;
import com.allpatekplatform.agreements.domain.repositories.AgreementRepository;
import com.allpatekplatform.shared.application.result.ApplicationError;
import com.allpatekplatform.shared.application.result.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AgreementCommandServiceImpl implements AgreementCommandService {

    private final AgreementRepository agreementRepository;

    public AgreementCommandServiceImpl(AgreementRepository agreementRepository) {
        this.agreementRepository = agreementRepository;
    }

    @Override
    @Transactional
    public Result<Agreement, ApplicationError> handle(SaveAgreementCommand command) {
        return Result.success(agreementRepository.save(new Agreement(command)));
    }
}

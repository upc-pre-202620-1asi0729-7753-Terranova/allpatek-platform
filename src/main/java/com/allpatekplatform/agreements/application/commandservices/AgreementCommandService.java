package com.allpatekplatform.agreements.application.commandservices;

import com.allpatekplatform.agreements.domain.model.aggregates.Agreement;
import com.allpatekplatform.agreements.domain.model.commands.DeleteAgreementCommand;
import com.allpatekplatform.agreements.domain.model.commands.SaveAgreementCommand;
import com.allpatekplatform.shared.application.result.ApplicationError;
import com.allpatekplatform.shared.application.result.Result;

public interface AgreementCommandService {

    Result<Agreement, ApplicationError> handle(SaveAgreementCommand command);

    Result<Void, ApplicationError> handle(DeleteAgreementCommand command);
}

package com.allpatekplatform.tracking.application.commandservices;

import com.allpatekplatform.tracking.domain.model.aggregates.MilestoneEvidence;
import com.allpatekplatform.tracking.domain.model.commands.DeleteEvidenceCommand;
import com.allpatekplatform.tracking.domain.model.commands.SaveEvidenceCommand;
import com.allpatekplatform.shared.application.result.ApplicationError;
import com.allpatekplatform.shared.application.result.Result;

public interface EvidenceCommandService {

    Result<MilestoneEvidence, ApplicationError> handle(SaveEvidenceCommand command);

    Result<Void, ApplicationError> handle(DeleteEvidenceCommand command);
}

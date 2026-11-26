package com.allpatekplatform.tracking.application.commandservices;

import com.allpatekplatform.tracking.domain.model.aggregates.MilestoneEvidence;
import com.allpatekplatform.tracking.domain.model.commands.DeleteEvidenceCommand;
import com.allpatekplatform.tracking.domain.model.commands.ReleaseEvidenceCommand;
import com.allpatekplatform.tracking.domain.model.commands.SaveEvidenceCommand;
import com.allpatekplatform.tracking.domain.model.commands.VerifyEvidenceCommand;
import com.allpatekplatform.shared.application.result.ApplicationError;
import com.allpatekplatform.shared.application.result.Result;

public interface EvidenceCommandService {

    Result<MilestoneEvidence, ApplicationError> handle(SaveEvidenceCommand command);

    Result<MilestoneEvidence, ApplicationError> handle(VerifyEvidenceCommand command);

    Result<MilestoneEvidence, ApplicationError> handle(ReleaseEvidenceCommand command);

    Result<Void, ApplicationError> handle(DeleteEvidenceCommand command);
}

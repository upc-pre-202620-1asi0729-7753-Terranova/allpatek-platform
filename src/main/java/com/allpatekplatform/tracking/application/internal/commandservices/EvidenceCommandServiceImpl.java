package com.allpatekplatform.tracking.application.internal.commandservices;

import com.allpatekplatform.tracking.application.commandservices.EvidenceCommandService;
import com.allpatekplatform.tracking.domain.model.aggregates.MilestoneEvidence;
import com.allpatekplatform.tracking.domain.model.commands.SaveEvidenceCommand;
import com.allpatekplatform.tracking.domain.repositories.EvidenceRepository;
import com.allpatekplatform.shared.application.result.ApplicationError;
import com.allpatekplatform.shared.application.result.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EvidenceCommandServiceImpl implements EvidenceCommandService {

    private final EvidenceRepository evidenceRepository;

    public EvidenceCommandServiceImpl(EvidenceRepository evidenceRepository) {
        this.evidenceRepository = evidenceRepository;
    }

    @Override
    @Transactional
    public Result<MilestoneEvidence, ApplicationError> handle(SaveEvidenceCommand command) {
        return Result.success(evidenceRepository.save(new MilestoneEvidence(command)));
    }
}

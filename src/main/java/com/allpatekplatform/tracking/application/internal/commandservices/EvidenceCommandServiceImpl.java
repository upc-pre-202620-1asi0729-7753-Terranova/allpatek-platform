package com.allpatekplatform.tracking.application.internal.commandservices;

import com.allpatekplatform.tracking.application.commandservices.EvidenceCommandService;
import com.allpatekplatform.tracking.domain.model.aggregates.MilestoneEvidence;
import com.allpatekplatform.tracking.domain.model.commands.DeleteEvidenceCommand;
import com.allpatekplatform.tracking.domain.model.commands.ReleaseEvidenceCommand;
import com.allpatekplatform.tracking.domain.model.commands.SaveEvidenceCommand;
import com.allpatekplatform.tracking.domain.model.commands.VerifyEvidenceCommand;
import com.allpatekplatform.tracking.domain.model.valueobjects.EvidenceStatus;
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

    @Override
    @Transactional
    public Result<MilestoneEvidence, ApplicationError> handle(VerifyEvidenceCommand command) {
        return transition(command.evidenceId(), EvidenceStatus.review, EvidenceStatus.verified);
    }

    @Override
    @Transactional
    public Result<MilestoneEvidence, ApplicationError> handle(ReleaseEvidenceCommand command) {
        return transition(command.evidenceId(), EvidenceStatus.verified, EvidenceStatus.accepted);
    }

    @Override
    @Transactional
    public Result<Void, ApplicationError> handle(DeleteEvidenceCommand command) {
        if (!evidenceRepository.existsById(command.id())) {
            return Result.failure(ApplicationError.notFound("EVIDENCE", command.id()));
        }
        evidenceRepository.deleteById(command.id());
        return Result.success(null);
    }

    private Result<MilestoneEvidence, ApplicationError> transition(
            String evidenceId,
            EvidenceStatus expected,
            EvidenceStatus next
    ) {
        var existing = evidenceRepository.findById(evidenceId);
        if (existing.isEmpty()) {
            return Result.failure(ApplicationError.notFound("EVIDENCE", evidenceId));
        }
        var evidence = existing.get();
        if (evidence.getStatus() != expected) {
            return Result.failure(ApplicationError.businessRuleViolation(
                    "EVIDENCE_STATUS",
                    "Evidence must be %s before moving to %s".formatted(expected, next)));
        }
        return Result.success(evidenceRepository.save(evidence.withStatus(next)));
    }
}

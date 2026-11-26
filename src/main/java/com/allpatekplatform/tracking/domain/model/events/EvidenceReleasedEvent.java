package com.allpatekplatform.tracking.domain.model.events;

import com.allpatekplatform.tracking.domain.model.aggregates.MilestoneEvidence;

/**
 * Domain event published when verified evidence is released and the milestone is accepted.
 */
public record EvidenceReleasedEvent(
        String evidenceId,
        String parcelId,
        String agreementId,
        int milestone
) {

    public static EvidenceReleasedEvent from(MilestoneEvidence evidence) {
        return new EvidenceReleasedEvent(
                evidence.getId().value(),
                evidence.getParcelId().value(),
                evidence.getAgreementId().value(),
                evidence.getMilestone().value());
    }
}

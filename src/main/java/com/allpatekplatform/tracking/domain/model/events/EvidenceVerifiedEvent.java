package com.allpatekplatform.tracking.domain.model.events;

import com.allpatekplatform.tracking.domain.model.aggregates.MilestoneEvidence;

/**
 * Domain event published when a merchant verifies milestone evidence.
 */
public record EvidenceVerifiedEvent(
        String evidenceId,
        String parcelId,
        String agreementId,
        int milestone
) {

    public static EvidenceVerifiedEvent from(MilestoneEvidence evidence) {
        return new EvidenceVerifiedEvent(
                evidence.getId().value(),
                evidence.getParcelId().value(),
                evidence.getAgreementId().value(),
                evidence.getMilestone().value());
    }
}

package com.allpatekplatform.tracking.interfaces.events;

/**
 * Published language of tracking when a merchant verifies milestone evidence.
 */
public record EvidenceVerifiedIntegrationEvent(
        String evidenceId,
        String parcelId,
        String agreementId,
        int milestone
) {
}

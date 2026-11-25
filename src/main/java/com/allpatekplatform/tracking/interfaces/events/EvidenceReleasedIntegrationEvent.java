package com.allpatekplatform.tracking.interfaces.events;

/**
 * Published language of tracking when a milestone is accepted and funds may be released.
 */
public record EvidenceReleasedIntegrationEvent(
        String evidenceId,
        String parcelId,
        String agreementId,
        int milestone
) {
}

package com.allpatekplatform.agreements.domain.model.events;

import com.allpatekplatform.agreements.domain.model.aggregates.Agreement;

/**
 * Domain event raised after both parties have signed an agreement and it has been persisted.
 */
public record AgreementFullySignedEvent(String agreementId, String parcelId, Long merchantProfileId) {

    public static AgreementFullySignedEvent from(Agreement agreement) {
        Long merchantProfileId = agreement.getMerchantProfileId() == null
                ? null
                : agreement.getMerchantProfileId().value();
        return new AgreementFullySignedEvent(
                agreement.getId().value(),
                agreement.getParcelId().value(),
                merchantProfileId);
    }
}

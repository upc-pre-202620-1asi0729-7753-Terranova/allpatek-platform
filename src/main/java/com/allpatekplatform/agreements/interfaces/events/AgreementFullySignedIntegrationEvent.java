package com.allpatekplatform.agreements.interfaces.events;

/**
 * Published language of the agreements context. Other bounded contexts listen to this
 * event instead of importing {@code agreements.domain}.
 */
public record AgreementFullySignedIntegrationEvent(String agreementId, String parcelId, Long merchantProfileId) {
}

package com.allpatekplatform.agreements.application.internal.eventhandlers;

import com.allpatekplatform.agreements.domain.model.events.AgreementFullySignedEvent;
import com.allpatekplatform.agreements.interfaces.events.AgreementFullySignedIntegrationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Translates the internal domain event into the integration event other contexts may consume.
 */
@Service("agreementsAgreementFullySignedEventHandler")
public class AgreementFullySignedEventHandler {

    private final ApplicationEventPublisher eventPublisher;

    public AgreementFullySignedEventHandler(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @EventListener
    public void on(AgreementFullySignedEvent event) {
        eventPublisher.publishEvent(new AgreementFullySignedIntegrationEvent(
                event.agreementId(),
                event.parcelId(),
                event.merchantProfileId()));
    }
}

package com.allpatekplatform.tracking.application.internal.eventhandlers;

import com.allpatekplatform.tracking.domain.model.events.EvidenceVerifiedEvent;
import com.allpatekplatform.tracking.interfaces.events.EvidenceVerifiedIntegrationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Translates the internal domain event into the integration event other contexts may consume.
 */
@Service("trackingEvidenceVerifiedEventHandler")
public class EvidenceVerifiedEventHandler {

    private final ApplicationEventPublisher eventPublisher;

    public EvidenceVerifiedEventHandler(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @EventListener
    public void on(EvidenceVerifiedEvent event) {
        eventPublisher.publishEvent(new EvidenceVerifiedIntegrationEvent(
                event.evidenceId(),
                event.parcelId(),
                event.agreementId(),
                event.milestone()));
    }
}

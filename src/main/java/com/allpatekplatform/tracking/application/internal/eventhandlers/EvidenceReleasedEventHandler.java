package com.allpatekplatform.tracking.application.internal.eventhandlers;

import com.allpatekplatform.tracking.domain.model.events.EvidenceReleasedEvent;
import com.allpatekplatform.tracking.interfaces.events.EvidenceReleasedIntegrationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Translates the internal domain event into the integration event other contexts may consume.
 */
@Service("trackingEvidenceReleasedEventHandler")
public class EvidenceReleasedEventHandler {

    private final ApplicationEventPublisher eventPublisher;

    public EvidenceReleasedEventHandler(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @EventListener
    public void on(EvidenceReleasedEvent event) {
        eventPublisher.publishEvent(new EvidenceReleasedIntegrationEvent(
                event.evidenceId(),
                event.parcelId(),
                event.agreementId(),
                event.milestone()));
    }
}

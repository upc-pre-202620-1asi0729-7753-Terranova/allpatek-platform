package com.allpatekplatform.profiles.application.internal.eventhandlers;

import com.allpatekplatform.profiles.domain.model.events.ProfileCreatedEvent;
import com.allpatekplatform.profiles.interfaces.events.ProfileCreatedIntegrationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Internal application-layer handler for the {@link ProfileCreatedEvent} domain event.
 *
 * <p>Translates the internal domain event into a {@link ProfileCreatedIntegrationEvent}
 * and re-publishes it on the Spring event bus.</p>
 */
@Service("profilesProfileCreatedEventHandler")
public class ProfileCreatedEventHandler {

    private final ApplicationEventPublisher eventPublisher;

    public ProfileCreatedEventHandler(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @EventListener
    public void on(ProfileCreatedEvent event) {
        eventPublisher.publishEvent(new ProfileCreatedIntegrationEvent(
                event.profileId(),
                event.fullName(),
                event.phone(),
                event.document(),
                event.address(),
                event.email(),
                event.company(),
                event.ruc(),
                event.activity()));
    }
}

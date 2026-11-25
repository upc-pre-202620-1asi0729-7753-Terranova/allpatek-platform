package com.allpatekplatform.profiles.interfaces.events;

/**
 * Integration event published by the {@code profiles} bounded context when a new
 * profile has been successfully created and persisted.
 *
 * <p>This is the published language of the {@code profiles} context. Other bounded
 * contexts must listen to this event rather than to internal domain events.</p>
 */
public record ProfileCreatedIntegrationEvent(
        Long profileId,
        String fullName,
        String phone,
        String document,
        String address,
        String email,
        String company,
        String ruc,
        String activity
) {
}

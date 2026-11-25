package com.allpatekplatform.profiles.domain.model.events;

import com.allpatekplatform.profiles.domain.model.aggregates.Profile;

/**
 * Domain event published when a new {@link Profile} is successfully created and persisted.
 */
public record ProfileCreatedEvent(
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

    public static ProfileCreatedEvent from(Profile profile) {
        return new ProfileCreatedEvent(
                profile.getId().value(),
                profile.getFullName().value(),
                profile.getPhone().value(),
                profile.getDocument().value(),
                profile.getAddress().value(),
                profile.getEmail(),
                profile.getCompany().company(),
                profile.getCompany().ruc(),
                profile.getCompany().activity());
    }
}

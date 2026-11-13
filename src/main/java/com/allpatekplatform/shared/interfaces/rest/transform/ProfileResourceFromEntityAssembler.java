package com.allpatekplatform.shared.interfaces.rest.transform;

import com.allpatekplatform.shared.domain.model.aggregates.Profile;
import com.allpatekplatform.shared.interfaces.rest.resources.ProfileResource;

public final class ProfileResourceFromEntityAssembler {

    private ProfileResourceFromEntityAssembler() {
    }

    public static ProfileResource toResourceFromEntity(Profile profile) {
        return new ProfileResource(
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

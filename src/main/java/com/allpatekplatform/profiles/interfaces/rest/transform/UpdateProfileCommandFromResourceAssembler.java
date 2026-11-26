package com.allpatekplatform.profiles.interfaces.rest.transform;

import com.allpatekplatform.profiles.domain.model.commands.UpdateProfileCommand;
import com.allpatekplatform.profiles.interfaces.rest.resources.UpdateProfileResource;

public final class UpdateProfileCommandFromResourceAssembler {

    private UpdateProfileCommandFromResourceAssembler() {
    }

    public static UpdateProfileCommand toCommandFromResource(Long id, UpdateProfileResource resource) {
        return new UpdateProfileCommand(
                id,
                resource.fullName(),
                resource.phone(),
                resource.document(),
                resource.address(),
                resource.email(),
                resource.company(),
                resource.ruc(),
                resource.activity());
    }
}

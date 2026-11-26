package com.allpatekplatform.profiles.interfaces.rest.transform;

import com.allpatekplatform.profiles.domain.model.commands.CreateProfileCommand;
import com.allpatekplatform.profiles.interfaces.rest.resources.CreateProfileResource;

public final class CreateProfileCommandFromResourceAssembler {

    private CreateProfileCommandFromResourceAssembler() {
    }

    public static CreateProfileCommand toCommandFromResource(CreateProfileResource resource, String email) {
        return new CreateProfileCommand(
                resource.fullName(),
                resource.phone(),
                resource.document(),
                resource.address(),
                email,
                resource.company(),
                resource.ruc(),
                resource.activity());
    }
}

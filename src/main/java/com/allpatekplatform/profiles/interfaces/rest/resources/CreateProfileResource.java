package com.allpatekplatform.profiles.interfaces.rest.resources;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateProfileResource(
        @NotBlank(message = "{validation.not-blank}")
        String fullName,
        String phone,
        String document,
        String address,
        @NotBlank(message = "{validation.not-blank}")
        @Email(message = "{validation.email}")
        String email,
        String company,
        String ruc,
        String activity
) {
}

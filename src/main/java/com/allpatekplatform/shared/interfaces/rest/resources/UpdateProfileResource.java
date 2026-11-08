package com.allpatekplatform.shared.interfaces.rest.resources;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UpdateProfileResource(
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

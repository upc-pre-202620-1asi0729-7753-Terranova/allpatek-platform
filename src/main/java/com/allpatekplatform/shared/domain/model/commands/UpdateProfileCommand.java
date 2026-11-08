package com.allpatekplatform.shared.domain.model.commands;

public record UpdateProfileCommand(
        Long id,
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

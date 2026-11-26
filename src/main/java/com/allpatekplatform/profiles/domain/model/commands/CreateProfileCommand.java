package com.allpatekplatform.profiles.domain.model.commands;

public record CreateProfileCommand(
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

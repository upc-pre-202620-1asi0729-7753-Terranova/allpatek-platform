package com.allpatekplatform.shared.interfaces.rest.resources;

public record ProfileResource(
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

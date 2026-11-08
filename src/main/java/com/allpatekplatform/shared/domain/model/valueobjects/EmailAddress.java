package com.allpatekplatform.shared.domain.model.valueobjects;

public record EmailAddress(String address) {

    public EmailAddress {
        if (address == null || address.isBlank()) {
            throw new IllegalArgumentException("Email must not be blank");
        }
        String normalized = address.trim();
        if (!normalized.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            throw new IllegalArgumentException("Email is invalid");
        }
        address = normalized;
    }
}

package com.allpatekplatform.shared.domain.model.valueobjects;

public record PersonName(String value) {

    public PersonName {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Profile name must not be empty.");
        }
        value = value.trim();
    }
}

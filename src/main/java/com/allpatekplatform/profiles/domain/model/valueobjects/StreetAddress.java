package com.allpatekplatform.profiles.domain.model.valueobjects;

public record StreetAddress(String value) {

    public StreetAddress {
        value = value == null ? "" : value.trim();
    }
}

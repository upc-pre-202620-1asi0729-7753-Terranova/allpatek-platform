package com.allpatekplatform.shared.domain.model.valueobjects;

public record StreetAddress(String value) {

    public StreetAddress {
        value = value == null ? "" : value.trim();
    }
}

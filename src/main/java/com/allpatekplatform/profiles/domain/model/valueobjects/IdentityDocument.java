package com.allpatekplatform.profiles.domain.model.valueobjects;

public record IdentityDocument(String value) {

    public IdentityDocument {
        value = value == null ? "" : value.trim();
    }
}

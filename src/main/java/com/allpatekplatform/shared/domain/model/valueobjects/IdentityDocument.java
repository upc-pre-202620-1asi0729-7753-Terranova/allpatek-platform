package com.allpatekplatform.shared.domain.model.valueobjects;

public record IdentityDocument(String value) {

    public IdentityDocument {
        value = value == null ? "" : value.trim();
    }
}

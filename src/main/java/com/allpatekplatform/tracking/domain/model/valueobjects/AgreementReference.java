package com.allpatekplatform.tracking.domain.model.valueobjects;

public record AgreementReference(String value) {

    public AgreementReference {
        value = value == null ? "" : value.trim();
    }

    public boolean isPresent() {
        return !value.isBlank();
    }
}

package com.allpatekplatform.agreements.domain.model.valueobjects;

public record AgreementId(String value) {

    public AgreementId {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Agreement must belong to a parcel.");
        }
        value = value.trim();
    }
}

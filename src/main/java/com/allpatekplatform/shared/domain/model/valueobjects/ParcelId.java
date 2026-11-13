package com.allpatekplatform.shared.domain.model.valueobjects;

public record ParcelId(String value) {

    public ParcelId {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Parcel id is required.");
        }
        value = value.trim();
    }
}

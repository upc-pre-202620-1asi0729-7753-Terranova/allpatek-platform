package com.allpatekplatform.parcels.domain.model.valueobjects;

public record ParcelName(String value) {

    public ParcelName {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Parcel name must not be empty.");
        }
        value = value.trim();
    }
}

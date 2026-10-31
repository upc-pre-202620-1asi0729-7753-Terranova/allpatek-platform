package com.allpatekplatform.parcels.domain.model.valueobjects;

public record ParcelLocation(String value) {

    public ParcelLocation {
        value = value == null ? "" : value.trim();
    }
}

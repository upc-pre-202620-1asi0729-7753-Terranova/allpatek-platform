package com.allpatekplatform.parcels.domain.model.valueobjects;

public record ParcelArea(String value) {

    public ParcelArea {
        value = value == null ? "" : value.trim();
    }
}

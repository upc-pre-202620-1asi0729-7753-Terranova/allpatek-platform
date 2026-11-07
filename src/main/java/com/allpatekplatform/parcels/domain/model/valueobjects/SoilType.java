package com.allpatekplatform.parcels.domain.model.valueobjects;

public record SoilType(String value) {

    public SoilType {
        value = value == null ? "" : value.trim();
    }
}

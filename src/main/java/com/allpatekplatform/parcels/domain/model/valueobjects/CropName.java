package com.allpatekplatform.parcels.domain.model.valueobjects;

public record CropName(String value) {

    public CropName {
        value = value == null ? "" : value.trim();
    }
}

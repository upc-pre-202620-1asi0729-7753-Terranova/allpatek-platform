package com.allpatekplatform.tracking.domain.model.valueobjects;

public record EvidenceId(String value) {

    public EvidenceId {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Evidence must belong to a parcel milestone.");
        }
        value = value.trim();
    }
}

package com.allpatekplatform.tracking.domain.model.valueobjects;

public record EvidenceNotes(String value) {

    public EvidenceNotes {
        value = value == null ? "" : value.trim();
    }
}

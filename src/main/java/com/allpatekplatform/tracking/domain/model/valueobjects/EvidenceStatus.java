package com.allpatekplatform.tracking.domain.model.valueobjects;

public enum EvidenceStatus {
    review,
    verified,
    accepted;

    public static EvidenceStatus from(String value) {
        if (value == null) {
            return review;
        }
        return switch (value.trim().toLowerCase()) {
            case "verified" -> verified;
            case "accepted" -> accepted;
            default -> review;
        };
    }
}

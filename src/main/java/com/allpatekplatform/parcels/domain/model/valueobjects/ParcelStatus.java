package com.allpatekplatform.parcels.domain.model.valueobjects;

public enum ParcelStatus {
    available,
    in_production,
    maintenance;

    public static ParcelStatus from(String value) {
        if (value == null) {
            return available;
        }
        return switch (value.trim().toLowerCase().replace('-', '_')) {
            case "in_production" -> in_production;
            case "maintenance" -> maintenance;
            default -> available;
        };
    }

    public String toApiValue() {
        return this == in_production ? "in-production" : name();
    }
}

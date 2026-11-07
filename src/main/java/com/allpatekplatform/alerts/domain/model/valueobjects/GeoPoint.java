package com.allpatekplatform.alerts.domain.model.valueobjects;

public record GeoPoint(double latitude, double longitude) {

    public GeoPoint {
        if (latitude < -90 || latitude > 90 || longitude < -180 || longitude > 180) {
            throw new IllegalArgumentException("Parcel coordinates are invalid.");
        }
    }
}

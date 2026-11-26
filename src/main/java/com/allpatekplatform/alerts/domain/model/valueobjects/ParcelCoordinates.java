package com.allpatekplatform.alerts.domain.model.valueobjects;

import com.allpatekplatform.shared.domain.model.valueobjects.ParcelId;

public record ParcelCoordinates(ParcelId parcelId, AlertParcelName name, GeoPoint point) {

    public double latitude() {
        return point.latitude();
    }

    public double longitude() {
        return point.longitude();
    }

    public static ParcelCoordinates fromLocation(String parcelId, String parcelName, String location) {
        if (location == null) throw new IllegalArgumentException("Parcel location is required.");
        String[] parts = location.split(",", -1);
        if (parts.length != 2) throw new IllegalArgumentException("Expected latitude,longitude.");
        try {
            double latitude = Double.parseDouble(parts[0].trim());
            double longitude = Double.parseDouble(parts[1].trim());
            return new ParcelCoordinates(
                    new ParcelId(parcelId),
                    new AlertParcelName(parcelName),
                    new GeoPoint(latitude, longitude));
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException("Parcel location must contain valid coordinates.", exception);
        }
    }
}

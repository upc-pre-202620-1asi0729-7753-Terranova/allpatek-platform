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
        if (location == null || !location.contains(",")) {
            return null;
        }
        String[] parts = location.split(",", 2);
        try {
            double latitude = Double.parseDouble(parts[0].trim());
            double longitude = Double.parseDouble(parts[1].trim());
            return new ParcelCoordinates(
                    new ParcelId(parcelId),
                    new AlertParcelName(parcelName),
                    new GeoPoint(latitude, longitude));
        } catch (IllegalArgumentException ignored) {
            return null;
        }
    }
}

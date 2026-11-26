package com.allpatekplatform.alerts.domain.model.queries;
public record GetClimateAlertsQuery(Long ownerId) {
    public GetClimateAlertsQuery {
        if (ownerId == null || ownerId <= 0) throw new IllegalArgumentException("A positive owner id is required.");
    }
}

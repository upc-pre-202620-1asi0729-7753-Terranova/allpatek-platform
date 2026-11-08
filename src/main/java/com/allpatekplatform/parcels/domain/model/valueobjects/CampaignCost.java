package com.allpatekplatform.parcels.domain.model.valueobjects;

public record CampaignCost(String value) {

    public CampaignCost {
        value = value == null ? "" : value.trim();
    }
}

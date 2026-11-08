package com.allpatekplatform.parcels.domain.model.valueobjects;

public record CampaignSchedule(int durationMonths, String startMonth) {

    public CampaignSchedule {
        durationMonths = Math.max(durationMonths, 0);
        startMonth = startMonth == null ? "" : startMonth.trim();
    }
}

package com.allpatekplatform.parcels.infrastructure.persistence.jpa.embeddables;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class CampaignSchedulePersistenceEmbeddable {

    @Column(name = "duration_months")
    private Integer durationMonths;

    @Column(name = "start_month", length = 20)
    private String startMonth;

    public CampaignSchedulePersistenceEmbeddable() {
    }

    public CampaignSchedulePersistenceEmbeddable(Integer durationMonths, String startMonth) {
        this.durationMonths = durationMonths;
        this.startMonth = startMonth;
    }

    public Integer getDurationMonths() {
        return durationMonths;
    }

    public void setDurationMonths(Integer durationMonths) {
        this.durationMonths = durationMonths;
    }

    public String getStartMonth() {
        return startMonth;
    }

    public void setStartMonth(String startMonth) {
        this.startMonth = startMonth;
    }
}

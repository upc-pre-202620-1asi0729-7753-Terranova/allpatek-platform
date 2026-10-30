package com.allpatekplatform.shared.infrastructure.persistence.jpa.embeddables;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class CompanyDetailsPersistenceEmbeddable {

    @Column(length = 160)
    private String company;

    @Column(length = 20)
    private String ruc;

    @Column(length = 160)
    private String activity;

    public CompanyDetailsPersistenceEmbeddable() {
    }

    public CompanyDetailsPersistenceEmbeddable(String company, String ruc, String activity) {
        this.company = company;
        this.ruc = ruc;
        this.activity = activity;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }
}

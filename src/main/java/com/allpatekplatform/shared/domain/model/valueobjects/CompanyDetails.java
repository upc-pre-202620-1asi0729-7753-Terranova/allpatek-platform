package com.allpatekplatform.shared.domain.model.valueobjects;

public record CompanyDetails(String company, String ruc, String activity) {

    public CompanyDetails {
        company = company == null ? "" : company.trim();
        ruc = ruc == null ? "" : ruc.trim();
        activity = activity == null ? "" : activity.trim();
    }
}

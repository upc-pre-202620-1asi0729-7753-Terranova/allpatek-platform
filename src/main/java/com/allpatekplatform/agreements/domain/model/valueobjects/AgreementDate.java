package com.allpatekplatform.agreements.domain.model.valueobjects;

public record AgreementDate(String value) {

    public AgreementDate {
        value = value != null && value.matches("\\d{4}-\\d{2}-\\d{2}") ? value : "";
    }
}

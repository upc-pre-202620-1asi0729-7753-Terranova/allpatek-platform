package com.allpatekplatform.agreements.domain.model.valueobjects;

public record MerchantName(String value) {

    public MerchantName {
        value = value == null ? "" : value.trim();
    }
}

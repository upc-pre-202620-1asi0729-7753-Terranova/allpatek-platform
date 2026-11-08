package com.allpatekplatform.shared.domain.model.valueobjects;

public record PhoneNumber(String value) {

    public PhoneNumber {
        value = value == null ? "" : value.trim();
    }
}

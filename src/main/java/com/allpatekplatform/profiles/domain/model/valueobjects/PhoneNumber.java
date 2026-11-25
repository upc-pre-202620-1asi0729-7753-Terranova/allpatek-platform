package com.allpatekplatform.profiles.domain.model.valueobjects;

public record PhoneNumber(String value) {

    public PhoneNumber {
        value = value == null ? "" : value.trim();
    }
}

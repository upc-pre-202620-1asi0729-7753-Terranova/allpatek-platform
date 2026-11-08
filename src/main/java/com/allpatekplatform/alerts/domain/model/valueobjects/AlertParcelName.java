package com.allpatekplatform.alerts.domain.model.valueobjects;

public record AlertParcelName(String value) {

    public AlertParcelName {
        value = value == null ? "" : value.trim();
    }
}

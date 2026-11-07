package com.allpatekplatform.shared.domain.model.valueobjects;

public record ProfileId(Long value) {

    public ProfileId {
        if (value != null && value < 1) {
            value = null;
        }
    }

    public boolean isAssigned() {
        return value != null;
    }
}

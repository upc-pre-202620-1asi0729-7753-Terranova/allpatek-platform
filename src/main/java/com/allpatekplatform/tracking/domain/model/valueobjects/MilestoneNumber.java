package com.allpatekplatform.tracking.domain.model.valueobjects;

public record MilestoneNumber(int value) {

    public MilestoneNumber {
        if (value < 1 || value > 4) {
            throw new IllegalArgumentException("Evidence must belong to a parcel milestone.");
        }
    }
}

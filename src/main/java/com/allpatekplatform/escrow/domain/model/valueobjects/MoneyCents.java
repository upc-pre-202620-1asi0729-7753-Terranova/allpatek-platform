package com.allpatekplatform.escrow.domain.model.valueobjects;

public record MoneyCents(int value) {

    public MoneyCents {
        if (value < 1) {
            throw new IllegalArgumentException("Card balance must be greater than zero.");
        }
    }
}

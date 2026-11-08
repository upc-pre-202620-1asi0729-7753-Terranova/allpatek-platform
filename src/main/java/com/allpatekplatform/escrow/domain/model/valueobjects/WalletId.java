package com.allpatekplatform.escrow.domain.model.valueobjects;

public record WalletId(String value) {

    public WalletId {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Card must belong to a merchant.");
        }
        value = value.trim();
    }
}

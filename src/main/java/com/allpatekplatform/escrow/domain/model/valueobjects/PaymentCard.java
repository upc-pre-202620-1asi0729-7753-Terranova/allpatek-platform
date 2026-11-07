package com.allpatekplatform.escrow.domain.model.valueobjects;

public record PaymentCard(String holder, String last4, String expiry, String brand) {

    public PaymentCard {
        if (holder == null || holder.isBlank() || last4 == null || !last4.matches("\\d{4}")
                || expiry == null || !expiry.matches("\\d{2}/\\d{2}")) {
            throw new IllegalArgumentException("Card details are incomplete.");
        }
        holder = holder.trim();
        brand = brand == null || brand.isBlank() ? "Tarjeta" : brand.trim();
    }
}

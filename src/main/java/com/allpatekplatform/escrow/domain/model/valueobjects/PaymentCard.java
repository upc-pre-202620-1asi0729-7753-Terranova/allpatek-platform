package com.allpatekplatform.escrow.domain.model.valueobjects;

public record PaymentCard(String holder, String number, String cvv, String last4, String expiry, String brand) {

    public PaymentCard {
        if (holder == null || holder.isBlank() || last4 == null || !last4.matches("\\d{4}")
                || expiry == null || !expiry.matches("\\d{2}/\\d{2}")) {
            throw new IllegalArgumentException("Card details are incomplete.");
        }
        holder = holder.trim();
        number = number == null ? "" : number.replaceAll("\\D", "");
        cvv = cvv == null ? "" : cvv.replaceAll("\\D", "");
        if (!number.isEmpty() && number.length() != 16) {
            throw new IllegalArgumentException("Card number must be 16 digits.");
        }
        if (!cvv.isEmpty() && (cvv.length() < 3 || cvv.length() > 4)) {
            throw new IllegalArgumentException("CVV must be 3 or 4 digits.");
        }
        brand = brand == null || brand.isBlank() ? "Tarjeta" : brand.trim();
    }
}

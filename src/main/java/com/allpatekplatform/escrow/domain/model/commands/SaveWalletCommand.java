package com.allpatekplatform.escrow.domain.model.commands;

public record SaveWalletCommand(
        String id,
        Long profileId,
        String holder,
        String number,
        String cvv,
        String last4,
        String expiry,
        String brand,
        int openingCents
) {
}

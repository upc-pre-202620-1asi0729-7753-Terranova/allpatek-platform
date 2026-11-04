package com.allpatekplatform.escrow.interfaces.rest.resources;

import jakarta.validation.constraints.NotNull;

public record WalletResource(
        String id,
        @NotNull(message = "{validation.not-null}")
        Long profileId,
        String holder,
        String last4,
        String expiry,
        String brand,
        Integer openingCents
) {
}

package com.allpatekplatform.escrow.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Escrow card token. PAN and CVV are never stored or returned.")
public record WalletResource(
        String id,
        Long profileId,
        @NotBlank(message = "{validation.not-blank}")
        String holder,
        @Schema(accessMode = Schema.AccessMode.WRITE_ONLY, description = "Optional PAN used only to derive last4. Never persisted.")
        String number,
        @Schema(accessMode = Schema.AccessMode.WRITE_ONLY, description = "Optional CVV. Ignored and never persisted.")
        String cvv,
        String last4,
        String expiry,
        String brand,
        Integer openingCents
) {
}

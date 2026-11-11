package com.allpatekplatform.escrow.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Card number and CVV are strings. The vault UI shows only the last 4 digits.")
public record WalletResource(
        String id,
        @NotNull(message = "{validation.not-null}")
        Long profileId,
        String holder,
        @Schema(type = "string", description = "Full 16-digit card number as text.")
        String number,
        @Schema(type = "string", description = "Card CVV as text, 3 or 4 digits.")
        String cvv,
        @Schema(description = "Last 4 digits derived from the number.", accessMode = Schema.AccessMode.READ_ONLY)
        String last4,
        String expiry,
        String brand,
        Integer openingCents
) {
}

package com.allpatekplatform.agreements.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AgreementResource(
        String id,
        @NotBlank(message = "{validation.not-blank}")
        String parcelId,
        String merchantName,
        @NotNull(message = "{validation.not-null}")
        Long merchantProfileId,
        Boolean merchantSigned,
        Boolean farmerSigned,
        String createdAt
) {
}

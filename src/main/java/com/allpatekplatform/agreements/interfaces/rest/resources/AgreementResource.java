package com.allpatekplatform.agreements.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;

public record AgreementResource(
        String id,
        @NotBlank(message = "{validation.not-blank}")
        String parcelId,
        String merchantName,
        Long merchantProfileId,
        Boolean merchantSigned,
        Boolean farmerSigned,
        String createdAt
) {
}

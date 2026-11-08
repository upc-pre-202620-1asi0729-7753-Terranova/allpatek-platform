package com.allpatekplatform.tracking.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EvidenceResource(
        String id,
        @NotBlank(message = "{validation.not-blank}")
        String parcelId,
        @NotNull(message = "{validation.not-null}")
        Integer milestone,
        String status,
        String notes,
        String fileName,
        String image
) {
}

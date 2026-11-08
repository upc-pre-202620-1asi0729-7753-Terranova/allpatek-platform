package com.allpatekplatform.parcels.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ParcelResource(
        @NotBlank(message = "{validation.not-blank}")
        String id,
        @NotNull(message = "{validation.not-null}")
        Long ownerId,
        @NotBlank(message = "{validation.not-blank}")
        String name,
        String status,
        String area,
        @NotBlank(message = "{validation.not-blank}")
        String location,
        String soilType,
        String campaignCost,
        Integer durationMonths,
        String startMonth,
        List<String> crops,
        String imageUrl,
        List<String> images
) {
}

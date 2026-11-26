package com.allpatekplatform.parcels.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record ParcelResource(
        @NotBlank(message = "{validation.not-blank}")
        String id,
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

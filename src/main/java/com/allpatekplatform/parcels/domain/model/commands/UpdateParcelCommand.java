package com.allpatekplatform.parcels.domain.model.commands;

import java.util.List;

public record UpdateParcelCommand(
        String id,
        Long ownerId,
        String name,
        String status,
        String area,
        String location,
        String soilType,
        String campaignCost,
        int durationMonths,
        String startMonth,
        List<String> crops,
        String imageUrl,
        List<String> images
) {
}

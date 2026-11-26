package com.allpatekplatform.tracking.domain.model.commands;

public record SaveEvidenceCommand(
        String id,
        String parcelId,
        String agreementId,
        int milestone,
        String status,
        String notes,
        String fileName,
        String image
) {
}

package com.allpatekplatform.agreements.domain.model.commands;

public record SaveAgreementCommand(
        String id,
        String parcelId,
        String merchantName,
        Long merchantProfileId,
        boolean merchantSigned,
        boolean farmerSigned,
        String createdAt
) {
}

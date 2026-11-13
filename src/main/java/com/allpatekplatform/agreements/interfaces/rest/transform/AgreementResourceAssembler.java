package com.allpatekplatform.agreements.interfaces.rest.transform;

import com.allpatekplatform.agreements.domain.model.aggregates.Agreement;
import com.allpatekplatform.agreements.domain.model.commands.SaveAgreementCommand;
import com.allpatekplatform.agreements.interfaces.rest.resources.AgreementResource;

public final class AgreementResourceAssembler {

    private AgreementResourceAssembler() {
    }

    public static AgreementResource toResourceFromEntity(Agreement agreement) {
        return new AgreementResource(
                agreement.getId().value(),
                agreement.getParcelId().value(),
                agreement.getMerchantName().value(),
                agreement.getMerchantProfileId().value(),
                agreement.getSignatures().merchantSigned(),
                agreement.getSignatures().farmerSigned(),
                agreement.getCreatedAt().value());
    }

    public static SaveAgreementCommand toCommand(String id, AgreementResource resource) {
        return new SaveAgreementCommand(
                id != null ? id : resource.id(),
                resource.parcelId(),
                resource.merchantName(),
                resource.merchantProfileId(),
                Boolean.TRUE.equals(resource.merchantSigned()),
                Boolean.TRUE.equals(resource.farmerSigned()),
                resource.createdAt());
    }
}

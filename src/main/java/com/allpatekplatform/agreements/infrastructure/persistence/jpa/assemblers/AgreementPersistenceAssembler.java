package com.allpatekplatform.agreements.infrastructure.persistence.jpa.assemblers;

import com.allpatekplatform.agreements.domain.model.aggregates.Agreement;
import com.allpatekplatform.agreements.domain.model.valueobjects.AgreementDate;
import com.allpatekplatform.agreements.domain.model.valueobjects.AgreementId;
import com.allpatekplatform.agreements.domain.model.valueobjects.ContractSignatures;
import com.allpatekplatform.agreements.domain.model.valueobjects.MerchantName;
import com.allpatekplatform.agreements.infrastructure.persistence.jpa.embeddables.ContractSignaturesPersistenceEmbeddable;
import com.allpatekplatform.agreements.infrastructure.persistence.jpa.entities.AgreementPersistenceEntity;
import com.allpatekplatform.shared.domain.model.valueobjects.ParcelId;
import com.allpatekplatform.shared.domain.model.valueobjects.ProfileId;

/**
 * Static assembler between agreement domain and persistence representations.
 */
public final class AgreementPersistenceAssembler {

    private AgreementPersistenceAssembler() {
    }

    public static Agreement toDomainFromPersistence(AgreementPersistenceEntity entity) {
        return new Agreement(
                new AgreementId(entity.getId()),
                new ParcelId(entity.getParcelId()),
                new MerchantName(entity.getMerchantName()),
                new ProfileId(entity.getMerchantProfileId()),
                toDomainFromPersistence(entity.getSignatures()),
                new AgreementDate(entity.getCreatedOn()));
    }

    public static AgreementPersistenceEntity toPersistenceFromDomain(Agreement agreement) {
        AgreementPersistenceEntity entity = new AgreementPersistenceEntity();
        entity.setId(agreement.getId().value());
        entity.setParcelId(agreement.getParcelId().value());
        entity.setMerchantName(agreement.getMerchantName().value());
        entity.setMerchantProfileId(agreement.getMerchantProfileId().value());
        entity.setSignatures(toPersistenceFromDomain(agreement.getSignatures()));
        entity.setCreatedOn(agreement.getCreatedAt().value());
        return entity;
    }

    private static ContractSignatures toDomainFromPersistence(ContractSignaturesPersistenceEmbeddable value) {
        return value == null
                ? new ContractSignatures(false, false)
                : new ContractSignatures(value.isMerchantSigned(), value.isFarmerSigned());
    }

    private static ContractSignaturesPersistenceEmbeddable toPersistenceFromDomain(ContractSignatures value) {
        return value == null ? null : new ContractSignaturesPersistenceEmbeddable(value.merchantSigned(), value.farmerSigned());
    }
}

package com.allpatekplatform.agreements.infrastructure.persistence.jpa.entities;

import com.allpatekplatform.agreements.infrastructure.persistence.jpa.embeddables.ContractSignaturesPersistenceEmbeddable;
import com.allpatekplatform.shared.infrastructure.persistence.jpa.entities.StringIdentifiablePersistenceEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "agreements")
public class AgreementPersistenceEntity extends StringIdentifiablePersistenceEntity {

    @Column(name = "parcel_id", nullable = false, length = 160)
    private String parcelId;

    @Column(name = "merchant_name", length = 160)
    private String merchantName;

    @Column(name = "merchant_profile_id")
    private Long merchantProfileId;

    @Embedded
    private ContractSignaturesPersistenceEmbeddable signatures;

    @Column(name = "signed_on", length = 20)
    private String createdOn;
}

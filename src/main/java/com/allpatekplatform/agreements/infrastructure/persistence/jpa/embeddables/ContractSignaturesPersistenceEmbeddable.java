package com.allpatekplatform.agreements.infrastructure.persistence.jpa.embeddables;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class ContractSignaturesPersistenceEmbeddable {

    @Column(name = "merchant_signed", nullable = false)
    private boolean merchantSigned;

    @Column(name = "farmer_signed", nullable = false)
    private boolean farmerSigned;

    public ContractSignaturesPersistenceEmbeddable() {
    }

    public ContractSignaturesPersistenceEmbeddable(boolean merchantSigned, boolean farmerSigned) {
        this.merchantSigned = merchantSigned;
        this.farmerSigned = farmerSigned;
    }

    public boolean isMerchantSigned() {
        return merchantSigned;
    }

    public void setMerchantSigned(boolean merchantSigned) {
        this.merchantSigned = merchantSigned;
    }

    public boolean isFarmerSigned() {
        return farmerSigned;
    }

    public void setFarmerSigned(boolean farmerSigned) {
        this.farmerSigned = farmerSigned;
    }
}

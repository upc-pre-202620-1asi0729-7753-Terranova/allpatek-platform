package com.allpatekplatform.agreements.domain.model.valueobjects;

public record ContractSignatures(boolean merchantSigned, boolean farmerSigned) {

    public boolean isComplete() {
        return merchantSigned && farmerSigned;
    }
}

package com.allpatekplatform.escrow.infrastructure.persistence.jpa.embeddables;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class PaymentCardPersistenceEmbeddable {

    @Column(nullable = false, length = 160)
    private String holder;

    @Column(name = "last4", nullable = false, length = 4)
    private String last4;

    @Column(nullable = false, length = 5)
    private String expiry;

    @Column(nullable = false, length = 40)
    private String brand;

    public PaymentCardPersistenceEmbeddable() {
    }

    public PaymentCardPersistenceEmbeddable(String holder, String last4, String expiry, String brand) {
        this.holder = holder;
        this.last4 = last4;
        this.expiry = expiry;
        this.brand = brand;
    }

    public String getHolder() {
        return holder;
    }

    public void setHolder(String holder) {
        this.holder = holder;
    }

    public String getLast4() {
        return last4;
    }

    public void setLast4(String last4) {
        this.last4 = last4;
    }

    public String getExpiry() {
        return expiry;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}

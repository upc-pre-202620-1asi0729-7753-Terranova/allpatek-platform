package com.allpatekplatform.escrow.infrastructure.persistence.jpa.entities;

import com.allpatekplatform.escrow.infrastructure.persistence.jpa.embeddables.PaymentCardPersistenceEmbeddable;
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
@Table(name = "wallets")
public class WalletPersistenceEntity extends StringIdentifiablePersistenceEntity {

    @Column(name = "profile_id", nullable = false)
    private Long profileId;

    @Embedded
    private PaymentCardPersistenceEmbeddable card;

    @Column(name = "opening_cents", nullable = false)
    private Integer openingCents;
}

package com.allpatekplatform.escrow.infrastructure.persistence.jpa.assemblers;

import com.allpatekplatform.escrow.domain.model.aggregates.Wallet;
import com.allpatekplatform.escrow.domain.model.valueobjects.MoneyCents;
import com.allpatekplatform.escrow.domain.model.valueobjects.PaymentCard;
import com.allpatekplatform.escrow.domain.model.valueobjects.WalletId;
import com.allpatekplatform.escrow.infrastructure.persistence.jpa.embeddables.PaymentCardPersistenceEmbeddable;
import com.allpatekplatform.escrow.infrastructure.persistence.jpa.entities.WalletPersistenceEntity;
import com.allpatekplatform.shared.domain.model.valueobjects.ProfileId;

/**
 * Static assembler between wallet domain and persistence representations.
 */
public final class WalletPersistenceAssembler {

    private WalletPersistenceAssembler() {
    }

    public static Wallet toDomainFromPersistence(WalletPersistenceEntity entity) {
        return new Wallet(
                new WalletId(entity.getId()),
                new ProfileId(entity.getProfileId()),
                toDomainFromPersistence(entity.getCard()),
                new MoneyCents(entity.getOpeningCents() == null ? 0 : entity.getOpeningCents()));
    }

    public static WalletPersistenceEntity toPersistenceFromDomain(Wallet wallet) {
        WalletPersistenceEntity entity = new WalletPersistenceEntity();
        entity.setId(wallet.getId().value());
        entity.setProfileId(wallet.getProfileId().value());
        entity.setCard(toPersistenceFromDomain(wallet.getCard()));
        entity.setOpeningCents(wallet.getOpeningBalance().value());
        return entity;
    }

    private static PaymentCard toDomainFromPersistence(PaymentCardPersistenceEmbeddable value) {
        return new PaymentCard(
                value.getHolder(),
                value.getNumber(),
                value.getCvv(),
                value.getLast4(),
                value.getExpiry(),
                value.getBrand());
    }

    private static PaymentCardPersistenceEmbeddable toPersistenceFromDomain(PaymentCard value) {
        return new PaymentCardPersistenceEmbeddable(
                value.holder(),
                value.number(),
                value.cvv(),
                value.last4(),
                value.expiry(),
                value.brand());
    }
}

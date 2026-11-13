package com.allpatekplatform.escrow.domain.model.aggregates;

import com.allpatekplatform.escrow.domain.model.commands.SaveWalletCommand;
import com.allpatekplatform.escrow.domain.model.valueobjects.MoneyCents;
import com.allpatekplatform.escrow.domain.model.valueobjects.PaymentCard;
import com.allpatekplatform.escrow.domain.model.valueobjects.WalletId;
import com.allpatekplatform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import com.allpatekplatform.shared.domain.model.valueobjects.ProfileId;
import lombok.Getter;

import java.util.Objects;

/**
 * Wallet aggregate root. Persistence lives in {@code WalletPersistenceEntity}.
 */
@Getter
public class Wallet extends AbstractDomainAggregateRoot<Wallet> {

    private final WalletId id;
    private final ProfileId profileId;
    private final PaymentCard card;
    private final MoneyCents openingBalance;

    public Wallet(WalletId id, ProfileId profileId, PaymentCard card, MoneyCents openingBalance) {
        this.id = Objects.requireNonNull(id, "id must not be null");
        this.profileId = Objects.requireNonNull(profileId, "profileId must not be null");
        this.card = Objects.requireNonNull(card, "card must not be null");
        this.openingBalance = Objects.requireNonNull(openingBalance, "openingBalance must not be null");
    }

    public Wallet(SaveWalletCommand command) {
        this(
                new WalletId(command.id()),
                new ProfileId(command.profileId()),
                new PaymentCard(
                        command.holder(),
                        command.number(),
                        command.cvv(),
                        command.last4(),
                        command.expiry(),
                        command.brand()),
                new MoneyCents(command.openingCents()));
    }
}

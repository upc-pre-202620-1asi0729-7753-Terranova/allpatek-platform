package com.allpatekplatform.escrow.interfaces.rest.transform;

import com.allpatekplatform.escrow.domain.model.aggregates.Wallet;
import com.allpatekplatform.escrow.domain.model.commands.SaveWalletCommand;
import com.allpatekplatform.escrow.interfaces.rest.resources.WalletResource;

public final class WalletResourceAssembler {

    private WalletResourceAssembler() {
    }

    public static WalletResource toResourceFromEntity(Wallet wallet) {
        return new WalletResource(
                wallet.getId().value(),
                wallet.getProfileId().value(),
                wallet.getCard().holder(),
                wallet.getCard().last4(),
                wallet.getCard().expiry(),
                wallet.getCard().brand(),
                wallet.getOpeningBalance().value());
    }

    public static SaveWalletCommand toCommand(String id, WalletResource resource) {
        return new SaveWalletCommand(
                id != null ? id : resource.id(),
                resource.profileId(),
                resource.holder(),
                resource.last4(),
                resource.expiry(),
                resource.brand(),
                resource.openingCents() == null ? 0 : resource.openingCents());
    }
}

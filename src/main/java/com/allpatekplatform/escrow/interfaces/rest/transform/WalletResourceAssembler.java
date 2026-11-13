package com.allpatekplatform.escrow.interfaces.rest.transform;

import com.allpatekplatform.escrow.domain.model.aggregates.Wallet;
import com.allpatekplatform.escrow.domain.model.commands.SaveWalletCommand;
import com.allpatekplatform.escrow.interfaces.rest.resources.WalletResource;

public final class WalletResourceAssembler {

    private WalletResourceAssembler() {
    }

    public static WalletResource toResourceFromEntity(Wallet wallet) {
        var card = wallet.getCard();
        return new WalletResource(
                wallet.getId().value(),
                wallet.getProfileId().value(),
                card.holder(),
                card.number(),
                card.cvv(),
                card.last4(),
                card.expiry(),
                card.brand(),
                wallet.getOpeningBalance().value());
    }

    public static SaveWalletCommand toCommand(String id, WalletResource resource) {
        var digits = digits(resource.number());
        var cvv = digits(resource.cvv());
        if (digits.length() != 16) {
            throw new IllegalArgumentException("Card number must be 16 digits.");
        }
        if (cvv.length() < 3 || cvv.length() > 4) {
            throw new IllegalArgumentException("CVV must be 3 or 4 digits.");
        }
        return new SaveWalletCommand(
                id != null ? id : resource.id(),
                resource.profileId(),
                resource.holder(),
                digits,
                cvv,
                digits.substring(12),
                resource.expiry(),
                brandFrom(digits, resource.brand()),
                resource.openingCents() == null ? 0 : resource.openingCents());
    }

    private static String digits(String value) {
        return value == null ? "" : value.replaceAll("\\D", "");
    }

    private static String brandFrom(String digits, String brand) {
        if (digits.startsWith("4")) {
            return "Visa";
        }
        if (digits.startsWith("5")) {
            return "Mastercard";
        }
        return brand;
    }
}

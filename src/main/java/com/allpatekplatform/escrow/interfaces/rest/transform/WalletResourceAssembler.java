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
                null,
                null,
                card.last4(),
                card.expiry(),
                card.brand(),
                wallet.getOpeningBalance().value());
    }

    public static SaveWalletCommand toCommand(String id, WalletResource resource) {
        var digits = digits(resource.number());
        var last4 = last4From(resource.last4(), digits);
        return new SaveWalletCommand(
                id != null ? id : resource.id(),
                resource.profileId(),
                resource.holder(),
                last4,
                resource.expiry(),
                brandFrom(digits, resource.brand()),
                resource.openingCents() == null ? 0 : resource.openingCents());
    }

    private static String digits(String value) {
        return value == null ? "" : value.replaceAll("\\D", "");
    }

    private static String last4From(String last4, String digits) {
        if (last4 != null && last4.matches("\\d{4}")) {
            return last4;
        }
        if (digits.length() >= 4) {
            return digits.substring(digits.length() - 4);
        }
        return last4;
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

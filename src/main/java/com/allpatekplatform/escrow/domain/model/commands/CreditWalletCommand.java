package com.allpatekplatform.escrow.domain.model.commands;

/**
 * Credits a merchant wallet after a milestone is accepted.
 */
public record CreditWalletCommand(Long profileId, int cents) {
}

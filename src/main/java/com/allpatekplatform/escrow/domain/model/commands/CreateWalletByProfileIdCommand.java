package com.allpatekplatform.escrow.domain.model.commands;

/**
 * Provisions a demonstration custody wallet for an existing merchant profile.
 */
public record CreateWalletByProfileIdCommand(Long profileId, String holder) {
}

package com.allpatekplatform.escrow.application.internal.eventhandlers;

import com.allpatekplatform.escrow.application.commandservices.WalletCommandService;
import com.allpatekplatform.escrow.domain.model.commands.CreateWalletByProfileIdCommand;
import com.allpatekplatform.profiles.interfaces.events.ProfileCreatedIntegrationEvent;
import com.allpatekplatform.shared.application.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * When a merchant profile is created, escrow provisions a demonstration custody wallet.
 * Farmer profiles are ignored. Failures never roll back the profile.
 */
@Service("escrowProfileCreatedEventHandler")
@Slf4j
public class ProfileCreatedEventHandler {

    private final WalletCommandService walletCommandService;

    public ProfileCreatedEventHandler(WalletCommandService walletCommandService) {
        this.walletCommandService = walletCommandService;
    }

    @EventListener
    public void on(ProfileCreatedIntegrationEvent event) {
        if (!isMerchant(event)) {
            return;
        }
        var result = walletCommandService.handle(new CreateWalletByProfileIdCommand(
                event.profileId(),
                event.fullName()));
        if (result instanceof Result.Failure(var error)) {
            log.warn("Failed to provision wallet for profile {}: {}", event.profileId(), error.message());
        }
    }

    private boolean isMerchant(ProfileCreatedIntegrationEvent event) {
        return notBlank(event.company()) || notBlank(event.ruc());
    }

    private boolean notBlank(String value) {
        return value != null && !value.isBlank();
    }
}

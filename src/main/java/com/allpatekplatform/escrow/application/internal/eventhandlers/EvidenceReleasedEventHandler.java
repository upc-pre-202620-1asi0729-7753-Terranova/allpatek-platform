package com.allpatekplatform.escrow.application.internal.eventhandlers;

import com.allpatekplatform.escrow.application.commandservices.WalletCommandService;
import com.allpatekplatform.escrow.application.internal.outboundservices.acl.ExternalAgreementService;
import com.allpatekplatform.escrow.domain.model.commands.CreditWalletCommand;
import com.allpatekplatform.shared.application.result.Result;
import com.allpatekplatform.tracking.interfaces.events.EvidenceReleasedIntegrationEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * When tracking accepts a milestone, escrow credits the merchant wallet.
 * Failures are logged and never roll back the evidence release.
 */
@Service("escrowEvidenceReleasedEventHandler")
@Slf4j
public class EvidenceReleasedEventHandler {

    static final int MILESTONE_CREDIT_CENTS = 25_000;

    private final ExternalAgreementService externalAgreementService;
    private final WalletCommandService walletCommandService;

    public EvidenceReleasedEventHandler(
            ExternalAgreementService externalAgreementService,
            WalletCommandService walletCommandService
    ) {
        this.externalAgreementService = externalAgreementService;
        this.walletCommandService = walletCommandService;
    }

    @EventListener
    public void on(EvidenceReleasedIntegrationEvent event) {
        var merchantProfileId = externalAgreementService.fetchMerchantProfileId(event.agreementId());
        if (merchantProfileId.isEmpty()) {
            log.warn(
                    "No merchant wallet target for released evidence {} on agreement {}",
                    event.evidenceId(),
                    event.agreementId());
            return;
        }
        var result = walletCommandService.handle(new CreditWalletCommand(
                merchantProfileId.get(),
                MILESTONE_CREDIT_CENTS));
        if (result instanceof Result.Failure(var error)) {
            log.warn(
                    "Failed to credit wallet for evidence {} (milestone {}): {}",
                    event.evidenceId(),
                    event.milestone(),
                    error.message());
        }
    }
}

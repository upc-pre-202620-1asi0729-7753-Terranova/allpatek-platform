package com.allpatekplatform.parcels.application.internal.eventhandlers;

import com.allpatekplatform.agreements.interfaces.events.AgreementFullySignedIntegrationEvent;
import com.allpatekplatform.parcels.application.commandservices.ParcelCommandService;
import com.allpatekplatform.parcels.domain.model.commands.MarkParcelInProductionCommand;
import com.allpatekplatform.shared.application.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * When both parties sign an agreement, the parcel leaves the available catalog
 * and enters production. Failures are logged and never roll back the agreement.
 */
@Service("parcelsAgreementFullySignedEventHandler")
@Slf4j
public class AgreementFullySignedEventHandler {

    private final ParcelCommandService parcelCommandService;

    public AgreementFullySignedEventHandler(ParcelCommandService parcelCommandService) {
        this.parcelCommandService = parcelCommandService;
    }

    @EventListener
    public void on(AgreementFullySignedIntegrationEvent event) {
        var result = parcelCommandService.handle(new MarkParcelInProductionCommand(event.parcelId()));
        if (result instanceof Result.Failure(var error)) {
            log.warn(
                    "Failed to mark parcel {} in production after agreement {}: {}",
                    event.parcelId(),
                    event.agreementId(),
                    error.message());
        }
    }
}

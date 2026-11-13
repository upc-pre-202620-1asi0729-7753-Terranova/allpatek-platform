package com.allpatekplatform.agreements.domain.model.aggregates;

import com.allpatekplatform.agreements.domain.model.commands.SaveAgreementCommand;
import com.allpatekplatform.agreements.domain.model.events.AgreementFullySignedEvent;
import com.allpatekplatform.agreements.domain.model.valueobjects.AgreementDate;
import com.allpatekplatform.agreements.domain.model.valueobjects.AgreementId;
import com.allpatekplatform.agreements.domain.model.valueobjects.ContractSignatures;
import com.allpatekplatform.agreements.domain.model.valueobjects.MerchantName;
import com.allpatekplatform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import com.allpatekplatform.shared.domain.model.valueobjects.ParcelId;
import com.allpatekplatform.shared.domain.model.valueobjects.ProfileId;
import lombok.Getter;

import java.util.Objects;

/**
 * Agreement aggregate root. Persistence lives in {@code AgreementPersistenceEntity}.
 */
@Getter
public class Agreement extends AbstractDomainAggregateRoot<Agreement> {

    private final AgreementId id;
    private final ParcelId parcelId;
    private final MerchantName merchantName;
    private final ProfileId merchantProfileId;
    private final ContractSignatures signatures;
    private final AgreementDate createdAt;

    public Agreement(
            AgreementId id,
            ParcelId parcelId,
            MerchantName merchantName,
            ProfileId merchantProfileId,
            ContractSignatures signatures,
            AgreementDate createdAt
    ) {
        this.id = Objects.requireNonNull(id, "id must not be null");
        this.parcelId = Objects.requireNonNull(parcelId, "parcelId must not be null");
        this.merchantName = merchantName == null ? new MerchantName("") : merchantName;
        this.merchantProfileId = merchantProfileId == null ? new ProfileId(null) : merchantProfileId;
        this.signatures = signatures == null ? new ContractSignatures(false, false) : signatures;
        this.createdAt = createdAt == null ? new AgreementDate("") : createdAt;
    }

    public Agreement(SaveAgreementCommand command) {
        this(
                new AgreementId(command.id()),
                new ParcelId(command.parcelId()),
                new MerchantName(command.merchantName()),
                new ProfileId(command.merchantProfileId()),
                new ContractSignatures(command.merchantSigned(), command.farmerSigned()),
                new AgreementDate(command.createdAt()));
    }

    public boolean isFullySigned() {
        return signatures.isComplete();
    }

    /**
     * Called by the repository adapter after persist when both signatures are present.
     */
    public void onFullySigned() {
        registerDomainEvent(AgreementFullySignedEvent.from(this));
    }
}

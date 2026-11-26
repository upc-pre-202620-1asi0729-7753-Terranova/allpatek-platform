package com.allpatekplatform.tracking.domain.model.aggregates;

import com.allpatekplatform.tracking.domain.model.commands.SaveEvidenceCommand;
import com.allpatekplatform.tracking.domain.model.events.EvidenceReleasedEvent;
import com.allpatekplatform.tracking.domain.model.events.EvidenceVerifiedEvent;
import com.allpatekplatform.tracking.domain.model.valueobjects.AgreementReference;
import com.allpatekplatform.tracking.domain.model.valueobjects.EvidenceFile;
import com.allpatekplatform.tracking.domain.model.valueobjects.EvidenceId;
import com.allpatekplatform.tracking.domain.model.valueobjects.EvidenceNotes;
import com.allpatekplatform.tracking.domain.model.valueobjects.EvidenceStatus;
import com.allpatekplatform.tracking.domain.model.valueobjects.MilestoneNumber;
import com.allpatekplatform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import com.allpatekplatform.shared.domain.model.valueobjects.ParcelId;
import lombok.Getter;

import java.util.Objects;

/**
 * Milestone evidence aggregate root. Persistence lives in {@code EvidencePersistenceEntity}.
 */
@Getter
public class MilestoneEvidence extends AbstractDomainAggregateRoot<MilestoneEvidence> {

    private final EvidenceId id;
    private final ParcelId parcelId;
    private final AgreementReference agreementId;
    private final MilestoneNumber milestone;
    private final EvidenceStatus status;
    private final EvidenceNotes notes;
    private final EvidenceFile file;

    public MilestoneEvidence(
            EvidenceId id,
            ParcelId parcelId,
            AgreementReference agreementId,
            MilestoneNumber milestone,
            EvidenceStatus status,
            EvidenceNotes notes,
            EvidenceFile file
    ) {
        this.id = Objects.requireNonNull(id, "id must not be null");
        this.parcelId = Objects.requireNonNull(parcelId, "parcelId must not be null");
        this.agreementId = agreementId == null ? new AgreementReference("") : agreementId;
        this.milestone = Objects.requireNonNull(milestone, "milestone must not be null");
        this.status = status == null ? EvidenceStatus.review : status;
        this.notes = notes == null ? new EvidenceNotes("") : notes;
        this.file = file == null ? new EvidenceFile("", "") : file;
    }

    public MilestoneEvidence(SaveEvidenceCommand command) {
        this(
                new EvidenceId(command.id()),
                new ParcelId(command.parcelId()),
                new AgreementReference(command.agreementId()),
                new MilestoneNumber(command.milestone()),
                EvidenceStatus.from(command.status()),
                new EvidenceNotes(command.notes()),
                new EvidenceFile(command.fileName(), command.image()));
    }

    public MilestoneEvidence withStatus(EvidenceStatus nextStatus) {
        var next = new MilestoneEvidence(id, parcelId, agreementId, milestone, nextStatus, notes, file);
        if (this.status == EvidenceStatus.review && nextStatus == EvidenceStatus.verified) {
            next.onVerified();
        }
        if (this.status == EvidenceStatus.verified && nextStatus == EvidenceStatus.accepted) {
            next.onReleased();
        }
        return next;
    }

    public void onVerified() {
        registerDomainEvent(EvidenceVerifiedEvent.from(this));
    }

    public void onReleased() {
        registerDomainEvent(EvidenceReleasedEvent.from(this));
    }
}
